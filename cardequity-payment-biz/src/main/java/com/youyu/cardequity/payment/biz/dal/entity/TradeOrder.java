package com.youyu.cardequity.payment.biz.dal.entity;

import com.youyu.cardequity.payment.biz.component.rabbitmq.RabbitmqSender;
import com.youyu.cardequity.payment.biz.enums.RabbitmqMessageEnum;
import com.youyu.cardequity.payment.dto.TradeOrderDto;
import com.youyu.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

import static com.alibaba.fastjson.JSON.toJSONString;
import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;
import static com.youyu.cardequity.common.base.util.DateUtil.*;
import static com.youyu.cardequity.common.base.util.StringUtil.eq;
import static com.youyu.cardequity.common.base.util.UuidUtil.uuid4NoRail;
import static com.youyu.cardequity.payment.dto.PayLogResponseDto.STATUS_PAYMENT_FAIL;
import static com.youyu.cardequity.payment.dto.PayLogResponseDto.STATUS_PAYMENT_SUCC;
import static com.youyu.cardequity.payment.dto.PayTradeRefundResponseDto.STATUS_FAIL;
import static com.youyu.cardequity.payment.dto.PayTradeRefundResponseDto.STATUS_SUCC;
import static org.apache.commons.lang3.time.DateUtils.addDays;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 交易系统订单数据:交易和退款
 */
@Getter
@Setter
@Table(name = "TB_TRADE_ORDER")
public class TradeOrder extends BaseEntity<String> {

    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 订单编号
     */
    @Column(name = "APP_SHEET_SERIAL_NO")
    private String appSheetSerialNo;

    /**
     * 交易账号
     */
    @Column(name = "TRANS_ACCOUNT_ID")
    private String transAccountId;

    /**
     * 客户号
     */
    @Column(name = "CLIENT_ID")
    private String clientId;

    /**
     * 客户姓名
     */
    @Column(name = "CLIENT_NAME")
    private String clientName;

    /**
     * 支付渠道号
     */
    @Column(name = "PAY_CHANNEL_NO")
    private String payChannelNo;

    /**
     * 支付编号
     */
    @Column(name = "PAY_LOG_ID")
    private String payLogId;

    /**
     * 订单金额
     */
    @Column(name = "ORDER_AMOUNT")
    private BigDecimal orderAmount;

    /**
     * 支付状态
     */
    @Column(name = "PAY_STATE")
    private String payState;

    /**
     * 支付系统退款流水号
     */
    @Column(name = "PAY_REFUND_ID")
    private String payRefundId;

    /**
     * 退款单号:退款批次号
     */
    @Column(name = "PAY_REFUND_NO")
    private String payRefundNo;

    /**
     * 退款金额
     */
    @Column(name = "REFUND_AMOUNT")
    private BigDecimal refundAmount;

    /**
     * 退款状态
     */
    @Column(name = "REFUND_STATUS")
    private String refundStatus;

    /**
     * 同步数据日期
     */
    @Column(name = "SYNC_DATA_DATE")
    private String syncDataDate;

    /**
     * 对账id
     */
    @Column(name = "PAY_CHECK_DEATAIL_ID")
    private String payCheckDeatailId;

    public TradeOrder() {

    }

    public TradeOrder(TradeOrderDto tradeOrderDto) {
        this.id = uuid4NoRail();
        this.appSheetSerialNo = tradeOrderDto.getAppSheetSerialNo();
        this.transAccountId = tradeOrderDto.getTransAccountId();
        this.clientId = tradeOrderDto.getClientId();
        this.clientName = tradeOrderDto.getClientName();
        this.payChannelNo = tradeOrderDto.getPayChannelNo();
        this.orderAmount = tradeOrderDto.getOrderAmount();
        this.payState = tradeOrderDto.getPayState();
        this.payRefundNo = tradeOrderDto.getPayRefundNo();
        this.refundAmount = tradeOrderDto.getRefundAmount();
        this.refundStatus = tradeOrderDto.getRefundStatus();
        this.payLogId = tradeOrderDto.getPayLogId();
        this.payRefundId = tradeOrderDto.getPayRefundId();
        this.syncDataDate = date2String(addDays(now(), -1), YYYYMMDD);
    }

    public void paySucc(RabbitmqMessageEnum rabbitmqMessageEnum) {
        this.payState = STATUS_PAYMENT_SUCC;
        senderTradeMessage(rabbitmqMessageEnum);
    }

    public void payFail(RabbitmqMessageEnum rabbitmqMessageEnum) {
        if (eq(this.payState, STATUS_PAYMENT_FAIL)) {
            return;
        }

        this.payState = STATUS_PAYMENT_FAIL;
        senderTradeMessage(rabbitmqMessageEnum);
    }

    public void refundSucc(RabbitmqMessageEnum rabbitmqMessageEnum) {
        this.refundStatus = STATUS_SUCC;
        senderRefundMessage(rabbitmqMessageEnum);
    }

    public void refundFail(RabbitmqMessageEnum rabbitmqMessageEnum) {
        if (eq(this.refundStatus, STATUS_FAIL)) {
            return;
        }

        this.refundStatus = STATUS_FAIL;
        senderRefundMessage(rabbitmqMessageEnum);
    }

    private void senderTradeMessage(RabbitmqMessageEnum rabbitmqMessageEnum) {
        getBeanByClass(RabbitmqSender.class).sendMessage(getTradeMessage(), rabbitmqMessageEnum);
    }

    private void senderRefundMessage(RabbitmqMessageEnum rabbitmqMessageEnum) {
        getBeanByClass(RabbitmqSender.class).sendMessage(getReturnMessage(), rabbitmqMessageEnum);
    }

    private String getReturnMessage() {
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setAppSheetSerialNo(this.appSheetSerialNo);
        tradeOrder.setPayRefundId(this.payRefundId);
        tradeOrder.setPayRefundNo(this.payChannelNo);
        tradeOrder.setRefundStatus(this.refundStatus);
        return toJSONString(tradeOrder);
    }

    private String getTradeMessage() {
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setAppSheetSerialNo(this.appSheetSerialNo);
        tradeOrder.setPayState(this.payState);
        return toJSONString(tradeOrder);
    }
}
