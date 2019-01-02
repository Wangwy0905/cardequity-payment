package com.youyu.cardequity.payment.biz.dal.entity;

import com.youyu.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

import static com.youyu.cardequity.common.base.util.DateUtil.date2String;
import static com.youyu.cardequity.common.base.util.DateUtil.now;
import static com.youyu.cardequity.common.base.util.UuidUtil.uuid4NoRail;
import static com.youyu.cardequity.payment.biz.enums.BackFlagEnum.NOT_NEED_REFUND;
import static com.youyu.cardequity.payment.biz.enums.BackFlagEnum.REFUNDED;
import static com.youyu.cardequity.payment.biz.enums.CheckStatusEnum.*;
import static com.youyu.cardequity.payment.biz.enums.RabbitmqMessageEnum.PAY_AFTER_PAY_FAIL_NOT_DAY_CUT_MESSAGE;
import static com.youyu.cardequity.payment.biz.enums.RabbitmqMessageEnum.PAY_AFTER_RETURN_FAIL_NOT_DAY_CUT_MESSAGE;
import static com.youyu.cardequity.payment.biz.help.constant.BusinessConstant.BUSIN_TYPE_REFUND;
import static com.youyu.cardequity.payment.biz.help.constant.BusinessConstant.BUSIN_TYPE_TRADE;
import static com.youyu.cardequity.payment.dto.PayLogResponseDto.STATUS_PAYMENT_FAIL;
import static com.youyu.cardequity.payment.dto.PayTradeRefundResponseDto.STATUS_FAIL;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 每笔对账信息
 */
@Getter
@Setter
@Table(name = "TB_PAY_CHANNEL_INFO")
public class PayCheckDeatail extends BaseDto<String> {

    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 银行流水号:文件中给到，如果是确定文件单边（Tb_PayLocalUnilateralCheckNum超出配置参数）=danbian+8位日期+9位计数器
     */
    @Column(name = "TRANCE_NO")
    private String tranceNo;

    /**
     * 对账单日期:格式统一YYYYMMDD
     */
    @Column(name = "CHECK_DATE")
    private String checkDate;

    /**
     * 渠道号:
     */
    @Column(name = "CHANNEL_NO")
    private String channelNo;

    /**
     * 申请时间:带时间格式：支付时银行记录的实际
     */
    @Column(name = "APP_DATE")
    private String appDate;

    /**
     * 支付金额:交易表支付金额：单边填0
     */
    @Column(name = "LOCAL_AMOUNT")
    private BigDecimal localAmount;

    /**
     * 支付系统金额:支付日志表金额：单边填0
     */
    @Column(name = "LOCAL_PAY_AMOUNT")
    private BigDecimal localPayAmount;

    /**
     * 支付状态:字典100005需要转义，交易表数据单边填0,退款复用该状态
     */
    @Column(name = "LOCAL_STATE")
    private String localState;

    /**
     * 支付系统状态:字典100005需要转义，支付日志表数据单边填0,退款复用该状态
     */
    @Column(name = "LOCAL_PAY_STATE")
    private String localPayState;

    /**
     * 文件支付金额:支付成功未报送
     */
    @Column(name = "FILE_AMOUNT")
    private BigDecimal fileAmount;

    /**
     * 文件支付状态:字典100009,退款复用该状态
     */
    @Column(name = "FILE_STATUS")
    private String fileStatus;

    /**
     * 退款状态:每个渠道定义不一样，需要解析后转义,无需退款的时候:填0
     */
    @Column(name = "RETURN_STATUS")
    private String returnStatus;

    /**
     * 文件支付时间:
     */
    @Column(name = "DEAL_DATE")
    private String dealDate;

    /**
     * 交易日期:格式统一YYYYMMDD
     */
    @Column(name = "TRANS_ACTION_DATE")
    private String transActionDate;

    /**
     * 客户编号:通过交易表关联
     */
    @Column(name = "CLIENT_ID")
    private String clientId;

    /**
     * 客户姓名:通过交易表关联
     */
    @Column(name = "CLIENT_NAME")
    private String clientName;

    /**
     * 银行代码:
     */
    @Column(name = "BANK_CODE")
    private String bankCode;

    /**
     * 银行账号:
     */
    @Column(name = "BANK_CARD_NO")
    private String bankCardNo;

    /**
     * 申请单号:交易申请单号
     */
    @Column(name = "APP_SHEET_SERIAL_NO")
    private String appSheetSerialNo;

    /**
     * 业务类型:可能需要转义
     */
    @Column(name = "BUSIN_TYPE")
    private String businType;

    /**
     * 对账天数:第一天对账时单边可以允许第二天再对，直到达到系统配置参数支付对账重试次数（一般设置为1）
     */
    @Column(name = "CHECK_NUM")
    private Integer checkNum;

    /**
     * 对账状态:字典100011
     */
    @Column(name = "CHECK_STATUS")
    private String checkStatus;

    /**
     * 退款处理标识:字典100010
     */
    @Column(name = "BACK_FLAG")
    private String backFlag;

    /**
     * 备注:
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 退款批次号
     */
    @Column(name = "REFUND_BATCH_NO")
    private String refundBatchNo;

    public PayCheckDeatail() {
        this.id = uuid4NoRail();
        this.transActionDate = date2String(now(), "YYYYMMDD");
        this.checkNum = 1;
    }

    public PayCheckDeatail(PayCheckFileDeatail payCheckFileDeatail) {
        this();
        this.tranceNo = payCheckFileDeatail.getTranceNo();
        this.checkDate = payCheckFileDeatail.getCheckDate();
        this.channelNo = payCheckFileDeatail.getChannelNo();
        this.appDate = payCheckFileDeatail.getAppDate();
        this.fileAmount = payCheckFileDeatail.getAppAmount();
        this.fileStatus = payCheckFileDeatail.getPayState();
        this.returnStatus = payCheckFileDeatail.getReturnStatus();
        this.dealDate = payCheckFileDeatail.getAppDate();
        this.appSheetSerialNo = payCheckFileDeatail.getAppSheetSerialNo();
        this.businType = payCheckFileDeatail.getBusinType();
        this.returnStatus = payCheckFileDeatail.getReturnStatus();
        this.refundBatchNo = payCheckFileDeatail.getRefundBatchNo();
    }

    public PayCheckDeatail(PayCheckFileDeatail payCheckFileDeatail, PayLog payLog) {
        this(payCheckFileDeatail);

        this.checkStatus = MAY_BE_FILE_UNILATERAL.getCode();
        this.backFlag = NOT_NEED_REFUND.getCode();

        this.localAmount = ZERO;
        this.localState = "0";
        if (isNull(payLog)) {
            this.localPayAmount = ZERO;
            this.localPayState = "0";
            return;
        }
        this.localPayAmount = payLog.getOccurBalance();
        this.localPayState = payLog.getPayState();
        this.clientId = payLog.getClientId();
        this.clientName = payLog.getClientName();
    }

    public PayCheckDeatail(PayCheckFileDeatail payCheckFileDeatail, TradeOrder tradeOrder, PayLog payLog) {
        this(payCheckFileDeatail);

        this.checkStatus = NORMAL.getCode();
        this.backFlag = NOT_NEED_REFUND.getCode();

        this.clientId = tradeOrder.getClientId();
        this.clientName = tradeOrder.getClientName();
        this.localAmount = tradeOrder.getOrderAmount();
        this.localState = tradeOrder.getPayState();

        this.localPayAmount = payLog.getOccurBalance();
        this.localPayState = payLog.getPayState();
    }

    public PayCheckDeatail(PayCheckFileDeatail payCheckFileDeatail, TradeOrder tradeOrder, PayTradeRefund payTradeRefund) {
        this(payCheckFileDeatail);

        this.checkStatus = NORMAL.getCode();
        this.backFlag = REFUNDED.getCode();

        this.clientId = tradeOrder.getClientId();
        this.clientName = tradeOrder.getClientName();
        this.localAmount = tradeOrder.getRefundAmount();
        this.localPayAmount = payTradeRefund.getRefundAmount();
        this.localState = tradeOrder.getRefundStatus();
        this.localPayState = payTradeRefund.getRefundStatus();
    }

    public PayCheckDeatail(PayCheckFileDeatail payCheckFileDeatail, PayTradeRefund payTradeRefund) {
        this(payCheckFileDeatail);

        this.checkStatus = MAY_BE_FILE_UNILATERAL.getCode();
        this.backFlag = REFUNDED.getCode();

        this.localAmount = ZERO;
        this.localState = "0";
        if (isNull(payTradeRefund)) {
            this.localPayAmount = ZERO;
            this.localPayState = "0";
            return;
        }
        this.localPayAmount = payTradeRefund.getRefundAmount();
        this.localPayState = payTradeRefund.getRefundStatus();
        this.clientId = payTradeRefund.getClientId();
        this.clientName = payTradeRefund.getClientName();
    }

    public PayCheckDeatail(PayLog payLog, TradeOrder tradeOrder) {
        this();
        this.businType = BUSIN_TYPE_TRADE;
        this.fileStatus = "";
        this.returnStatus = "";

        this.channelNo = payLog.getPayChannelNo();
        this.clientId = payLog.getClientId();
        this.clientName = payLog.getClientName();
        this.appSheetSerialNo = payLog.getAppSheetSerialNo();
        this.localPayAmount = payLog.getOccurBalance();

        this.localAmount = tradeOrder.getOrderAmount();
    }

    public PayCheckDeatail(PayTradeRefund payTradeRefund, TradeOrder tradeOrder) {
        this();
        this.businType = BUSIN_TYPE_REFUND;
        this.fileStatus = "";
        this.returnStatus = "";

        this.channelNo = payTradeRefund.getChannelNo();
        this.clientId = payTradeRefund.getClientId();
        this.clientName = payTradeRefund.getClientName();
        this.appSheetSerialNo = payTradeRefund.getAppSheetSerialNo();
        this.localPayAmount = payTradeRefund.getRefundAmount();

        this.localAmount = tradeOrder.getRefundAmount();
    }

    public void dayCut4Trade(PayLog payLog, TradeOrder tradeOrder) {
        this.localState = tradeOrder.getPayState();
        this.localPayState = payLog.getPayState();

        this.checkStatus = MAY_BE_TRADE_UNILATERAL.getCode();
        this.backFlag = NOT_NEED_REFUND.getCode();
    }

    public void dayCut4Refund(PayTradeRefund payTradeRefund, TradeOrder tradeOrder) {
        this.localState = tradeOrder.getRefundStatus();
        this.localPayState = payTradeRefund.getRefundStatus();
        this.checkStatus = MAY_BE_REFUND_UNILATERAL.getCode();
        this.backFlag = NOT_NEED_REFUND.getCode();
    }

    public void notDayCut4Trade(PayLog payLog, TradeOrder tradeOrder) {
        this.localState = STATUS_PAYMENT_FAIL;
        this.localPayState = STATUS_PAYMENT_FAIL;
        this.fileStatus = STATUS_PAYMENT_FAIL;
        this.returnStatus = STATUS_FAIL;
        this.checkStatus = NORMAL.getCode();
        this.backFlag = NOT_NEED_REFUND.getCode();
        payLog.payFail();
        tradeOrder.payFail(PAY_AFTER_PAY_FAIL_NOT_DAY_CUT_MESSAGE);
    }

    public void notDayCut4Refund(PayTradeRefund payTradeRefund, TradeOrder tradeOrder) {
        this.localState = STATUS_FAIL;
        this.localPayState = STATUS_FAIL;
        this.fileStatus = STATUS_FAIL;
        this.returnStatus = STATUS_FAIL;
        this.checkStatus = NORMAL.getCode();
        this.backFlag = NOT_NEED_REFUND.getCode();
        payTradeRefund.refundFail();
        tradeOrder.refundFail(PAY_AFTER_RETURN_FAIL_NOT_DAY_CUT_MESSAGE);
    }

    public void beforeDayCut4Trade(PayLog payLog, TradeOrder tradeOrder) {
        this.checkNum = this.checkNum + 1;

        this.localState = STATUS_PAYMENT_FAIL;
        this.localPayState = STATUS_PAYMENT_FAIL;
        this.fileStatus = STATUS_PAYMENT_FAIL;
        this.returnStatus = STATUS_FAIL;
        this.checkStatus = NORMAL.getCode();
        this.backFlag = NOT_NEED_REFUND.getCode();
        payLog.payFail();
        tradeOrder.payFail(PAY_AFTER_PAY_FAIL_NOT_DAY_CUT_MESSAGE);
    }

    public void beforeDayCut4Refund(PayTradeRefund payTradeRefund, TradeOrder tradeOrder) {
        this.checkNum = this.checkNum + 1;

        this.localState = STATUS_FAIL;
        this.localPayState = STATUS_FAIL;
        this.fileStatus = STATUS_FAIL;
        this.returnStatus = STATUS_FAIL;
        this.checkStatus = NORMAL.getCode();
        this.backFlag = NOT_NEED_REFUND.getCode();
        payTradeRefund.refundFail();
        tradeOrder.refundFail(PAY_AFTER_RETURN_FAIL_NOT_DAY_CUT_MESSAGE);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
