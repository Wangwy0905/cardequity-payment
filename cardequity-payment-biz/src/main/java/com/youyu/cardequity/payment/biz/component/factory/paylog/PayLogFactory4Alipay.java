package com.youyu.cardequity.payment.biz.component.factory.paylog;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.dal.dao.PayLogMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog4Alipay;
import com.youyu.cardequity.payment.dto.PayLogDto;
import com.youyu.common.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.payment.biz.enums.RouteVoIdFlagEnum.FAIL;
import static com.youyu.cardequity.payment.biz.enums.RouteVoIdFlagEnum.NORMAL;
import static com.youyu.cardequity.payment.enums.PaymentResultCodeEnum.PAYMENT_SUCCESS_ORDER_CANNOT_REPETITION_PAY;
import static java.util.Objects.isNull;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Alipay Pay支付工厂
 */
@StatusAndStrategyNum(superClass = PayLogFactory.class, number = "1", describe = "支付宝支付创建工厂")
@Component
public class PayLogFactory4Alipay extends PayLogFactory {

    @Autowired
    private PayLogMapper payLogMapper;

    @Override
    public PayLog createPayLog(PayLogDto payLogDto) {
        checkPayLog(payLogDto);
        PayLog4Alipay payLog4Alipay = new PayLog4Alipay(payLogDto);
        payLogMapper.insertSelective(payLog4Alipay);
        return payLog4Alipay;
    }

    private void checkPayLog(PayLogDto payLogDto) {
        String appSheetSerialNo = payLogDto.getAppSheetSerialNo();
        PayLog payLog = payLogMapper.getByAppSheetSerialNoRouteVoIdFlag(appSheetSerialNo, NORMAL.getCode());
        if (isNull(payLog)) {
            return;
        }

        if (!payLog.canRepetitionPay()) {
            throw new BizException(PAYMENT_SUCCESS_ORDER_CANNOT_REPETITION_PAY);
        }

        payLogMapper.updateByAppSheetSerialNoRouteVoIdFlag(appSheetSerialNo, FAIL.getCode());
    }
}
