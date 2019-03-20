package com.youyu.cardequity.payment.biz.component.status.paylog;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;
import static com.youyu.cardequity.payment.dto.PayLogResponseDto.STATUS_PAYMENT_SUCC;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 支付状态:支付成功
 */
@StatusAndStrategyNum(superClass = PayLogStatus.class, number = STATUS_PAYMENT_SUCC, describe = "支付成功")
@Component
public class PayLogStatus4PaymentSucc extends PayLogStatus {

    @Override
    public PayLogStatus paymentSucc() {
        return getBeanByClass(PayLogStatus4PaymentSucc.class);
    }

    @Override
    public PayLogStatus paymentFail() {
        return getBeanByClass(PayLogStatus4PaymentFail.class);
    }

    @Override
    public boolean canPayTradeClose() {
        return false;
    }

    @Override
    public boolean canRepetitionPay() {
        return false;
    }

    @Override
    public boolean createPayRefund() {
        return true;
    }

    @Override
    public boolean isPaySucc() {
        return true;
    }
}
