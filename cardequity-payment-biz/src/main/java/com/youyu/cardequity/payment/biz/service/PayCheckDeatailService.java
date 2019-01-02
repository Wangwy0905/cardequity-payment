package com.youyu.cardequity.payment.biz.service;

import com.youyu.cardequity.payment.biz.dal.entity.PayCheckFileDeatail;
import com.youyu.cardequity.payment.biz.dal.entity.TradeOrder;
import com.youyu.cardequity.payment.dto.PayCheckDeatailDto;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 对账信息管理 Service
 */
public interface PayCheckDeatailService {

    /**
     * 根据对账单进行对账
     *
     * @param payCheckDeatailDto
     * @param payCheckDeatailService
     */
    void reconciliation(PayCheckDeatailDto payCheckDeatailDto, PayCheckDeatailService payCheckDeatailService);

    /**
     * 执行对账单对交易和退款进行对账
     *
     * @param payCheckFileDeatail
     */
    void executeBill2TradeAndRefund(PayCheckFileDeatail payCheckFileDeatail);

    /**
     * 执行交易和退款来对对账单进行对账(主要是日切导致的数据)
     * 日切:20号11:59分做的交易可能在支付机构是21号12:01,那么对账单可能22号来,
     * 但是如果支付机构只发成功的对账单的话,如果22号对账单还是没有,则可以认为交易或者退款是失败的
     *
     * @param tradeOrder
     */
    void executeTradeAndRefund2Bill(TradeOrder tradeOrder);
}
