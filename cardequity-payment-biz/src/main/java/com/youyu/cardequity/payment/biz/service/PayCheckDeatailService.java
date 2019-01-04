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
     * <p>
     * 文件对交易或者退款
     * 1:文件有,交易或者退款都没有 对账状态:可能文件单边
     * 2:文件有,交易有,状态一致,对账状态:正常 状态不一致,对账状态:文件单边
     * 3:文件有,退款有,状态一致,对账状态:正常 状态不一致,对账状态:文件单边
     * <p>
     * 交易或退款对文件
     * 4:交易有,文件有,状态一致,对账状态:正常 状态不一致,对账状态:文件单边
     * 5:交易有,文件没有
     * a:日切: 对账状态:可能交易单边
     * b:非日切:对账状态:正常
     * c:前一天日切:对账状态:正常
     * <p>
     * 6:退款有,文件有,状态一致,对账状态:正常 状态不一致,对账状态:文件单边
     * 7:退款有,文件没有
     * a:日切: 对账状态:可能退款单边
     * b:非日切:对账状态:正常
     * c:前一天日切:对账状态:正常
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
