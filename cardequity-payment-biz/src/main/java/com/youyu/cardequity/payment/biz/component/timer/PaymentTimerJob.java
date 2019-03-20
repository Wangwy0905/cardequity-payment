package com.youyu.cardequity.payment.biz.component.timer;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.youyu.cardequity.payment.biz.component.strategy.timer.TimerStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanInstance;
import static org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2019年1月28日 下午10:00:00
 * @work 定时任务调度
 */
@JobHandler(value = "paymentTimerJobHandler")
@Component
@Slf4j
public class PaymentTimerJob extends IJobHandler {

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        log.info("方法PaymentTimerJob.execute:对应定时任务请求参数信息:[{}]", param);
        try {
            TimerStrategy timerStrategy = getBeanInstance(TimerStrategy.class, param);
            timerStrategy.execute();
        } catch (Exception ex) {
            log.error("方法PaymentTimerJob.execute:执行异常信息:[{}]", getFullStackTrace(ex));
        }
        return ReturnT.SUCCESS;
    }
}
