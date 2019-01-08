package com.youyu.cardequity.payment.biz.component.monitor;

import com.youyu.cardequity.payment.biz.dal.dao.ExceptionLogMapper;
import com.youyu.cardequity.payment.biz.dal.entity.ExceptionLog;
import com.youyu.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.alibaba.fastjson.JSON.toJSONString;
import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;
import static com.youyu.cardequity.common.base.util.UuidUtil.uuid4NoRail;
import static com.youyu.cardequity.payment.biz.help.constant.BusinessConstant.EXCEPTION_TYPE_BUSINESS;
import static com.youyu.cardequity.payment.biz.help.constant.BusinessConstant.EXCEPTION_TYPE_NON_BUSINESS;
import static org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 异常监听Monitor
 */
@Slf4j
@Aspect
@Component
public class ExceptionMonitor {

    @Value("${storage.switch:true}")
    private boolean storageSwitch;

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 8, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(200));

    @Pointcut("execution( * com.youyu.cardequity.payment.biz.service..*.*(..))")
    public void exceptionMonitorPoint() {

    }

    @Around(value = "exceptionMonitorPoint()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            threadPoolExecutor.execute(() -> disposeException(throwable, proceedingJoinPoint));
            throw throwable;
        }
    }

    /**
     * 业务前期考虑异步记表来记录异常
     *
     * @param throwable
     * @param proceedingJoinPoint
     */
    private void disposeException(Throwable throwable, ProceedingJoinPoint proceedingJoinPoint) {
        ExceptionLogMapper exceptionLogMapper = getBeanByClass(ExceptionLogMapper.class);
        String methodName = proceedingJoinPoint.getSignature().getName();
        String parameter = toJSONString(proceedingJoinPoint.getArgs());
        String exceptionId = uuid4NoRail();

        if (throwable instanceof BizException) {
            BizException bizException = (BizException) throwable;
            String code = bizException.getCode();
            String desc = bizException.getDesc();
            log.error("异常编号:[{}],业务方法:[{}],输入参数:[{}],执行对应的业务异常码:[{}],业务异常描述:[{}]", exceptionId, methodName, parameter, code, desc);
            if (storageSwitch) {
                exceptionLogMapper.insertSelective(new ExceptionLog(exceptionId, methodName, parameter, code, desc, EXCEPTION_TYPE_BUSINESS));
            }
            return;
        }

        String fullStackTrace = getFullStackTrace(throwable);
        log.error("异常编号:[{}],业务方法:[{}],输入参数:[{}],执行的异常信息:[{}]", exceptionId, methodName, parameter, fullStackTrace);
        if (storageSwitch) {
            exceptionLogMapper.insertSelective(new ExceptionLog(exceptionId, methodName, parameter, fullStackTrace, EXCEPTION_TYPE_NON_BUSINESS));
        }
    }

}
