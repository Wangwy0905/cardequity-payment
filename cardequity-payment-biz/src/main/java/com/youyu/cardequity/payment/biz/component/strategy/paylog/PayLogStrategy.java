package com.youyu.cardequity.payment.biz.component.strategy.paylog;


import com.youyu.cardequity.payment.biz.dal.entity.PayLog;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Pay支付策略
 */
public abstract class PayLogStrategy {

    /**
     * 支付策略
     *
     * @param payLog
     * @return
     */
    public abstract void executePay(PayLog payLog);
}
