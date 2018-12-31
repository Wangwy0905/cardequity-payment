package com.youyu.cardequity.payment.biz.dal.entity;

import com.youyu.cardequity.payment.biz.component.rabbitmq.RabbitmqSender;
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
import static com.youyu.cardequity.common.base.util.StringUtil.eq;
import static com.youyu.cardequity.common.base.util.UuidUtil.uuid4NoRail;
import static com.youyu.cardequity.payment.biz.enums.RabbitmqMessageEnum.PAY_AFTER_PAY_FAIL_NOT_DAY_CUT_MESSAGE;
import static com.youyu.cardequity.payment.dto.PayLogResponseDto.STATUS_PAYMENT_FAIL;

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
     * 退款单号
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
    }

    public void payFail() {
        if (eq(this.payState, STATUS_PAYMENT_FAIL)) {
            return;
        }

        this.payState = STATUS_PAYMENT_FAIL;
        getBeanByClass(RabbitmqSender.class).sendMessage(getPayFailMessage(), PAY_AFTER_PAY_FAIL_NOT_DAY_CUT_MESSAGE);
    }

    private String getPayFailMessage() {
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setAppSheetSerialNo(this.appSheetSerialNo);
        tradeOrder.setPayState(this.payState);
        return toJSONString(tradeOrder);
    }
}
