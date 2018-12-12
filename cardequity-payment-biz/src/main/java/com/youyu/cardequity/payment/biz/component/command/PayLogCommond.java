package com.youyu.cardequity.payment.biz.component.command;

import com.youyu.cardequity.payment.biz.dal.entity.PayLog;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Pay支付命令
 */
public abstract class PayLogCommond {

    /**
     * 执行命令
     *
     * @param payLog
     * @param t
     * @param <T>
     * @param <R>
     * @return
     */
    public abstract <T, R> R executeCmd(PayLog payLog, T t);
}
