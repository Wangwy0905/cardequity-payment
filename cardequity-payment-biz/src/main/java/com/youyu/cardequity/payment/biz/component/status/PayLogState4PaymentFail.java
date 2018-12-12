package com.youyu.cardequity.payment.biz.component.status;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 支付状态:支付失败
 */
@StatusAndStrategyNum(superClass = PayLogState.class, number = "4", describe = "支付失败")
@Component
public class PayLogState4PaymentFail extends PayLogState {

    @Override
    public PayLogState paymentSucc() {
        return getBeanByClass(PayLogState4PaymentSucc.class);
    }

    @Override
    public PayLogState paymentFail() {
        return getBeanByClass(PayLogState4PaymentFail.class);
    }
}
