package com.youyu.cardequity.payment.biz.component.strategy.paycheckfiledeatail;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.component.rabbitmq.RabbitmqSender;
import com.youyu.cardequity.payment.biz.dal.dao.PayCheckDeatailMapper;
import com.youyu.cardequity.payment.biz.dal.dao.PayLogMapper;
import com.youyu.cardequity.payment.biz.dal.dao.PayTradeRefundMapper;
import com.youyu.cardequity.payment.biz.dal.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.alibaba.fastjson.JSON.toJSONString;
import static com.youyu.cardequity.common.base.util.StringUtil.eq;
import static com.youyu.cardequity.payment.biz.enums.BackFlagEnum.NEED_REFUND;
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
                payLog.payAfterBill2TradeSucc("支付宝盘后对账支付状态为支付成功!");
                payLogMapper.updateByPayAfterBill2TradeSucc(payLog);

                payCheckDeatail.setBackFlag(NEED_REFUND.getCode());
            }
            rabbitmqSender.sendMessage(toJSONString(tradeOrder.getAppSheetSerialNo()), PAY_AFTER_REFUND_MESSAGE);
        }
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
        PayTradeRefund payTradeRefund = payTradeRefundMapper.getById(tradeOrder.getPayRefundNo());
        PayCheckDeatail payCheckDeatail = new PayCheckDeatail(payCheckFileDeatail, tradeOrder, payTradeRefund);
        if (!eq(payCheckFileDeatail.getReturnStatus(), payTradeRefund.getRefundStatus())) {
            if (!payTradeRefund.isRefundSucc()) {
                payTradeRefund.refundAfterBill2TradeRefund("支付宝盘活对账退款状态为退款成功!");
                payTradeRefundMapper.updateByRefundAfterBill2TradeRefund(payTradeRefund);
            }
            rabbitmqSender.sendMessage(toJSONString(payTradeRefund.getId()), PAY_AFTER_REFUND_STATUS_MESSAGE);
        }
        payCheckDeatailMapper.insertSelective(payCheckDeatail);
    }

    @Override
    public void doTrade2Bill(TradeOrder tradeOrder, PayCheckFileDeatail payCheckFileDeatail) {
        // TODO: 2018/12/28
        // 状态一致: 正常
        // 状态不一致: 退款 :通知交易系统
    }

    @Override
    public void doTrade2BillRefund(TradeOrder tradeOrder, PayCheckFileDeatail payCheckFileDeatail) {
        // TODO: 2018/12/28
        // 退款状态一致: 正常
        // 退款状态不一致: 通知交易修改成退款状态即可
    }
}
