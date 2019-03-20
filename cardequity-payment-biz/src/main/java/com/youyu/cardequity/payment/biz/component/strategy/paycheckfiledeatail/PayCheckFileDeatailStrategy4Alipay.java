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

import static com.youyu.cardequity.payment.biz.enums.BackFlagEnum.NEED_REFUND;
import static com.youyu.cardequity.payment.biz.enums.CheckStatusEnum.INCONSISTENT_STATE;
import static com.youyu.cardequity.payment.biz.enums.RabbitmqMessageEnum.PAY_AFTER_REFUND_MESSAGE;
import static com.youyu.cardequity.payment.biz.enums.RabbitmqMessageEnum.PAY_AFTER_REFUND_STATUS_MESSAGE;
import static org.apache.commons.lang3.StringUtils.isBlank;

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
        boolean isExist = deleteExistPayCheckDeatail(tradeOrder.getPayCheckDeatailId());

        PayCheckDeatail payCheckDeatail = new PayCheckDeatail(payCheckFileDeatail, tradeOrder, payLog);
        if (isExist) {
            payCheckDeatail.AddCheckNum();
        }

        if (!payLog.isPaySucc()) {
            payLog.payAfterBill2TradeSucc();
            payLogMapper.updateStatusByPayAfter(payLog);
        }

        if (!tradeOrder.isPaySucc()) {
            tradeOrder.paySucc(PAY_AFTER_REFUND_MESSAGE);

            payCheckDeatail.setBackFlag(NEED_REFUND.getCode());
            payCheckDeatail.setCheckStatus(INCONSISTENT_STATE.getCode());
            payCheckDeatail.setRemark("文件和交易状态不符");
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
        boolean isExist = deleteExistPayCheckDeatail(tradeOrder.getPayCheckDeatailId());

        PayCheckDeatail payCheckDeatail = new PayCheckDeatail(payCheckFileDeatail, tradeOrder, payTradeRefund);
        if (isExist) {
            payCheckDeatail.AddCheckNum();
        }

        if (!payTradeRefund.isRefundSucc()) {
            payTradeRefund.refundAfterBill2TradeRefund();
            payTradeRefundMapper.updateStatusByRefundAfter(payTradeRefund);
        }

        if (!tradeOrder.isRefundSucc()) {
            tradeOrder.refundSucc(PAY_AFTER_REFUND_STATUS_MESSAGE);

            payCheckDeatail.setCheckStatus(INCONSISTENT_STATE.getCode());
            payCheckDeatail.setRemark("文件和退款状态不符");
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

    private boolean deleteExistPayCheckDeatail(String payCheckDeatailId) {
        if (isBlank(payCheckDeatailId)) {
            return false;
        }

        payCheckDeatailMapper.deleteById(payCheckDeatailId);
        return true;
    }
}
