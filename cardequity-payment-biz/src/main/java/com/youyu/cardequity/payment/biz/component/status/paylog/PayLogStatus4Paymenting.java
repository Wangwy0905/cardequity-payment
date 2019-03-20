package com.youyu.cardequity.payment.biz.component.status.paylog;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;
import static com.youyu.cardequity.payment.dto.PayLogResponseDto.STATUS_PAYMENTING;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 支付状态:支付中
 */
@StatusAndStrategyNum(superClass = PayLogStatus.class, number = STATUS_PAYMENTING, describe = "支付中")
@Component
public class PayLogStatus4Paymenting extends PayLogStatus {

    @Override
    public PayLogStatus paymentSucc() {
        return getBeanByClass(PayLogStatus4PaymentSucc.class);
    }

    @Override
    public PayLogStatus paymentFail() {
        return getBeanByClass(PayLogStatus4PaymentFail.class);
    }

    @Override
    public PayLogStatus paymenting() {
        return getBeanByClass(PayLogStatus4Paymenting.class);
    }
}
