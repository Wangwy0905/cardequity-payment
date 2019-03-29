package com.youyu.cardequity.payment.biz.component.command.paylog;

import com.youyu.cardequity.payment.biz.dal.entity.PayLog;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Pay支付命令
 */
public abstract class PayLogCommand {

    /**
     * 执行命令
     *
     * @param payLog
     * @param t
     * @param <T>
     * @return
     */
    public abstract <T> void executeCmd(PayLog payLog, T t);
}
