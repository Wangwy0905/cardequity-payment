package com.youyu.cardequity.payment.biz.component.status;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 支付状态:支付排队中
 */
@StatusAndStrategyNum(superClass = PayLogState.class, number = "1", describe = "支付排队中")
@Component
public class PayLogState4PaymentQueuing extends PayLogState {

    @Override
    public PayLogState paymentQueuing() {
        return getBeanByClass(PayLogState4Paymenting.class);
    }

    @Override
    public boolean canPayTradeQuery() {
        return false;
    }
}
