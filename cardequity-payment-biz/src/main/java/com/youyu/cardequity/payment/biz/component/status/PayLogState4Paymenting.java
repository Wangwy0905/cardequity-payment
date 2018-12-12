package com.youyu.cardequity.payment.biz.component.status;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 支付状态:支付中
 */
@StatusAndStrategyNum(superClass = PayLogState.class, number = "2", describe = "支付中")
@Component
public class PayLogState4Paymenting extends PayLogState {

    @Override
    public PayLogState paymentSucc() {
        return getBeanByClass(PayLogState4PaymentSucc.class);
    }

    @Override
    public PayLogState paymentFail() {
        return getBeanByClass(PayLogState4PaymentFail.class);
    }

    @Override
    public PayLogState paymenting() {
        return getBeanByClass(PayLogState4Paymenting.class);
    }
}
