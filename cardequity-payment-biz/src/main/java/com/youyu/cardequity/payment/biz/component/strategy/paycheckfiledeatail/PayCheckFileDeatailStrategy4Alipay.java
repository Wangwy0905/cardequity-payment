package com.youyu.cardequity.payment.biz.component.strategy.paycheckfiledeatail;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.component.rabbitmq.RabbitmqSender;
import com.youyu.cardequity.payment.biz.dal.dao.PayCheckDeatailMapper;
import com.youyu.cardequity.payment.biz.dal.dao.PayLogMapper;
import com.youyu.cardequity.payment.biz.dal.dao.PayTradeRefundMapper;
import com.youyu.cardequity.payment.biz.dal.dao.TradeOrderMapper;
import com.youyu.cardequity.payment.biz.dal.entity.*;
import com.youyu.cardequity.payment.biz.enums.AlipayDayCutEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.common.base.util.StringUtil.eq;
import static com.youyu.cardequity.payment.biz.enums.BackFlagEnum.NEED_REFUND;
import static com.youyu.cardequity.payment.biz.enums.CheckStatusEnum.FILE_UNILATERAL;
import static com.youyu.cardequity.payment.biz.enums.CheckStatusEnum.REFUNDED;
import static com.youyu.cardequity.payment.biz.enums.RabbitmqMessageEnum.PAY_AFTER_REFUND_MESSAGE;
import static com.youyu.cardequity.payment.biz.enums.RabbitmqMessageEnum.PAY_AFTER_REFUND_STATUS_MESSAGE;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 对账策略:支付宝
 */
@Slf4j
@StatusAndStrategyNum(superClass = PayCheckFileDeatailStrategy.class, number = "1", describe = "支付宝对账策略")
@Component
public class PayCheckFileDeatailStrategy4Alipay extends PayCheckFileDeatailStrategy {

    @Autowired
    private PayCheckDeatailMapper payCheckDeatailMapper;
    @Autowired
    private PayLogMapper payLogMapper;
    @Autowired
    private PayTradeRefundMapper payTradeRefundMapper;
    @Autowired
    private TradeOrderMapper tradeOrderMapper;

    @Autowired
    private RabbitmqSender rabbitmqSender;

    /**
     * 状态一致: 正常
     * 状态不一致: 退款:通知交易系统进行退款
     *
     * @param payCheckFileDeatail
     * @param tradeOrder
     */
    @Override
    public void doBill2Trade(PayCheckFileDeatail payCheckFileDeatail, TradeOrder tradeOrder) {
        PayLog payLog = payLogMapper.getById(tradeOrder.getPayLogId());
        PayCheckDeatail payCheckDeatail = new PayCheckDeatail(payCheckFileDeatail, tradeOrder, payLog);

        if (!eq(payCheckFileDeatail.getPayState(), tradeOrder.getPayState())) {
            if (!payLog.isPaySucc()) {
                payLog.payAfterBill2TradeSucc();
                payLogMapper.updateStatusByPayAfter(payLog);
            }
            tradeOrder.paySucc(PAY_AFTER_REFUND_MESSAGE);

            payCheckDeatail.setBackFlag(NEED_REFUND.getCode());
            payCheckDeatail.setCheckStatus(FILE_UNILATERAL.getCode());
        }
        tradeOrder.setPayCheckDeatailId(payCheckDeatail.getId());
        tradeOrderMapper.updatePayStatusPayCheckDeatailIdByPayAfter(tradeOrder);

        payCheckDeatailMapper.insertSelective(payCheckDeatail);
    }

    /**
     * 退款状态一致: 正常
     * 退款状态不一致: 通知交易修改成退款状态即可
     *
     * @param payCheckFileDeatail
     * @param tradeOrder
     */
    @Override
    public void doBill2TradeRefund(PayCheckFileDeatail payCheckFileDeatail, TradeOrder tradeOrder) {
        PayTradeRefund payTradeRefund = payTradeRefundMapper.getById(tradeOrder.getPayRefundId());
        PayCheckDeatail payCheckDeatail = new PayCheckDeatail(payCheckFileDeatail, tradeOrder, payTradeRefund);
        if (!eq(payCheckFileDeatail.getReturnStatus(), payTradeRefund.getRefundStatus())) {
            if (!payTradeRefund.isRefundSucc()) {
                payTradeRefund.refundAfterBill2TradeRefund();
                payTradeRefundMapper.updateStatusByRefundAfter(payTradeRefund);
            }
            tradeOrder.refundSucc(PAY_AFTER_REFUND_STATUS_MESSAGE);

            payCheckDeatail.setCheckStatus(REFUNDED.getCode());
        }
        tradeOrder.setPayCheckDeatailId(payCheckDeatail.getId());
        tradeOrderMapper.updateReturnStatusPayCheckDeatailIdByRefundAfter(tradeOrder);

        payCheckDeatailMapper.insertSelective(payCheckDeatail);
    }

    @Override
    public void doTrade2BillNotFile(TradeOrder tradeOrder) {
        PayLog payLog = payLogMapper.getById(tradeOrder.getPayLogId());
        AlipayDayCutEnum alipayDayCutEnum = payLog.getAlipayDayCutEnum();
        alipayDayCutEnum.doTrade2BillNotFile(payLog, tradeOrder);

        tradeOrderMapper.updatePayStatusPayCheckDeatailIdByPayAfter(tradeOrder);
        payLogMapper.updateStatusByPayAfter(payLog);
    }

    @Override
    public void doTrade2BillRefundNotFile(TradeOrder tradeOrder) {
        PayTradeRefund payTradeRefund = payTradeRefundMapper.getById(tradeOrder.getPayRefundId());
        AlipayDayCutEnum alipayDayCutEnum = payTradeRefund.getAlipayDayCutEnum();
        alipayDayCutEnum.doTrade2BillRefundNotFile(payTradeRefund, tradeOrder);

        tradeOrderMapper.updateReturnStatusPayCheckDeatailIdByRefundAfter(tradeOrder);
        payTradeRefundMapper.updateStatusByRefundAfter(payTradeRefund);

    }
}
