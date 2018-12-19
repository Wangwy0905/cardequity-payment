package com.youyu.cardequity.payment.biz.component.factory.paytraderefund;

import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayTradeRefund;
import com.youyu.cardequity.payment.dto.PayTradeRefundDto;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 退款工厂
 */
public abstract class PayTradeRefundFactory {

    /**
     * 创建退款
     *
     * @param tradeRefundApplyDto
     * @param payLog
     * @return
     */
    public abstract PayTradeRefund createPayRefund(PayTradeRefundDto tradeRefundApplyDto, PayLog payLog);
}
