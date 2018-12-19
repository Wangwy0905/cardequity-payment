package com.youyu.cardequity.payment.biz.component.strategy.paytraderefund;

import com.youyu.cardequity.payment.biz.dal.entity.PayTradeRefund;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 退款策略
 */
public abstract class PayTradeRefundStrategy {

    /**
     * 退款策略
     *
     * @param payTradeRefund
     * @return
     */
    public abstract void executePayTradeRefund(PayTradeRefund payTradeRefund);
}
