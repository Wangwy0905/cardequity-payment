package com.youyu.cardequity.payment.biz.component.strategy;


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
     * @param <T>
     * @return
     */
    public abstract <T> T executePay(PayLog payLog);
}
