package com.youyu.cardequity.payment.biz.component.status;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 支付状态:未支付
 */
@StatusAndStrategyNum(superClass = PayLogState.class, number = "0", describe = "未支付")
@Component
public class PayLogState4NonPayment extends PayLogState {

    @Override
    public PayLogState paymentQueuing() {
        return getBeanByClass(PayLogState4NonPayment.class);
    }

    @Override
    public PayLogState paymenting() {
        return getBeanByClass(PayLogState4Paymenting.class);
    }
}
