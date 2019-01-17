package com.youyu.cardequity.payment.biz.dal.entity;

import com.youyu.cardequity.payment.biz.component.status.paylog.PayLogStatus;
import com.youyu.cardequity.payment.biz.component.status.paylog.PayLogStatus4NonPayment;
import com.youyu.cardequity.payment.biz.enums.AlipayDayCutEnum;
import com.youyu.cardequity.payment.dto.PayLogDto;
import com.youyu.cardequity.payment.dto.TradeCloseDto;
import com.youyu.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;
import static com.youyu.cardequity.common.base.util.StatusAndStrategyNumUtil.getNumber;
import static com.youyu.cardequity.common.base.util.UuidUtil.uuid4NoRail;
import static com.youyu.cardequity.payment.biz.enums.AlipayDayCutEnum.getAlipayDayCutEnumByTime;
import static java.time.LocalDateTime.now;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work PayLog支付日志
 */
@Getter
@Setter
@Table(name = "TB_PAY_LOG")
public class PayLog extends BaseEntity<String> {

    private static final long serialVersionUID = 8124639491945139690L;

    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    protected String id;
    /**
     * 系统日期:支付系统暂不做日切，填自然日
     */
    @Column(name = "INIT_DATE")
    protected LocalDate initDate;

    /**
     * 交易日期:由支付方传入
     */
    @Column(name = "TRANS_ACTION_DATE")
    protected LocalDate transActionDate;

    /**
     * 申请单号:订单编号：业务系统传入
     */
    @Column(name = "APP_SHEET_SERIAL_NO")
    protected String appSheetSerialNo;

    /**
     * 业务代码:
     */
    @Column(name = "BUSIN_CODE")
    protected String businCode;

    /**
     * 批次号:由支付方传入
     */
    @Column(name = "BATCH_NO")
    protected String batchNo;

    /**
     * 客户号:
     */
    @Column(name = "CLIENT_ID")
    protected String clientId;

    /**
     * 交易账号:
     */
    @Column(name = "TRANS_ACCOUNT_ID")
    protected String transAccountId;

    /**
     * 客户姓名:
     */
    @Column(name = "CLIENT_NAME")
    protected String clientName;

    /**
     * 银行代码:
     */
    @Column(name = "BANK_CODE")
    protected String bankCode;

    /**
     * 银行卡号:
     */
    @Column(name = "BANK_CARD_NO")
    protected String bankCardNo;

    /**
     * 支付状态:字典100005
     */
    @Column(name = "STATE")
    protected PayLogStatus state;

    /**
     * 发生金额:
     */
    @Column(name = "OCCUR_BALANCE")
    protected BigDecimal occurBalance;

    /**
     * 支付渠道号:
     */
    @Column(name = "PAY_CHANNEL_NO")
    protected String payChannelNo;

    /**
     * 证件类别:
     */
    @Column(name = "CERTIFICATE_TYPE")
    protected String certificateType;

    /**
     * 证件号码:
     */
    @Column(name = "CERTIFICATE_NO")
    protected String certificateNo;

    /**
     * 备注:
     */
    @Column(name = "REMARK")
    protected String remark;

    /**
     * 银行支付编号:银行返回的
     */
    @Column(name = "PAY_SERIAL_NO")
    protected String paySerialNo;

    /**
     * 平台支付编号:平台返回的
     */
    @Column(name = "THIRD_SERIAL_NO")
    protected String thirdSerialNo;

    /**
     * 返回代码:
     */
    @Column(name = "RESP_CODE")
    protected String respCode;

    /**
     * 返回信息:
     */
    @Column(name = "RESP_INFO")
    protected String respInfo;

    /**
     * 路由抛弃标识:类似IsEnable的作用，0-标识正常的发生；1-标识在智能路由过程中走该渠道失败
     */
    @Column(name = "ROUTE_VO_ID_FLAG")
    protected String routeVoIdFlag;

    /**
     * 鉴别类型
     */
    @Column(name = "TYPE")
    protected String type;

    /**
     * true:关闭成功
     */
    @Column(name = "TRADE_CLOSE_FLAG")
    protected Boolean tradeCloseFlag;

    public PayLog() {
        this.id = uuid4NoRail();
        setCreateTime(now());
    }

    public PayLog(PayLogDto payLogDto) {
        this();
        this.occurBalance = payLogDto.getOccurBalance();
        this.initDate = payLogDto.getInitDate();
        this.transAccountId = payLogDto.getTransAccountId();
        this.appSheetSerialNo = payLogDto.getAppSheetSerialNo();
        this.clientId = payLogDto.getClientId();
        this.clientName = payLogDto.getClientName();
        this.certificateType = payLogDto.getCertificateType();
        this.certificateNo = payLogDto.getCertificateNo();
        this.remark = payLogDto.getRemark();
        this.state = getBeanByClass(PayLogStatus4NonPayment.class);
        this.payChannelNo = payLogDto.getPayChannelNo();
        this.businCode = payLogDto.getBusinCode();
        this.routeVoIdFlag = "0";
        this.tradeCloseFlag = false;
    }

    public void tradeClose(TradeCloseDto tradeCloseDto) {
        throw new RuntimeException("该交易不支持关闭!");
    }

    public void tradeQuery() {
        throw new RuntimeException("该交易不支持查询!");
    }

    public boolean canPayTradeQuery() {
        return state.canPayTradeQuery();
    }

    public boolean canPayTradeClose() {
        return state.canPayTradeClose();
    }

    public boolean canRepetitionPay() {
        return state.canRepetitionPay();
    }

    public boolean createPayRefund() {
        return state.createPayRefund();
    }

    public boolean isPaySucc() {
        return state.isPaySucc();
    }

    public void payAfterBill2TradeSucc() {
        this.state = state.paymentSucc();
    }

    public AlipayDayCutEnum getAlipayDayCutEnum() {
        return getAlipayDayCutEnumByTime(getCreateTime());
    }

    public void payFail() {
        this.state = state.paymentFail();
    }

    public void callPay() {
        this.state = state.paymenting();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getPayState() {
        return getNumber(this.state);
    }

    public String getPayLogId() {
        return this.id;
    }
}
