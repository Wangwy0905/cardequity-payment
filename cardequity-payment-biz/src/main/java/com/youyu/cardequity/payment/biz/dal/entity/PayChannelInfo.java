package com.youyu.cardequity.payment.biz.dal.entity;

import com.youyu.cardequity.payment.biz.component.factory.paycheckfiledeatail.PayCheckFileDeatailFactory;
import com.youyu.cardequity.payment.biz.component.factory.paylog.PayLogFactory;
import com.youyu.cardequity.payment.biz.component.factory.paytraderefund.PayTradeRefundFactory;
import com.youyu.cardequity.payment.biz.component.strategy.paycheckfiledeatail.PayCheckFileDeatailStrategy;
import com.youyu.cardequity.payment.biz.component.strategy.paylog.PayLogStrategy;
import com.youyu.cardequity.payment.biz.component.strategy.paytraderefund.PayTradeRefundStrategy;
import com.youyu.cardequity.payment.biz.enums.PayChannelStateEnum;
import com.youyu.cardequity.payment.dto.PayChannelInfoDto;
import com.youyu.cardequity.payment.dto.PayCheckFileDeatailDto;
import com.youyu.cardequity.payment.dto.PayLogDto;
import com.youyu.cardequity.payment.dto.PayTradeRefundDto;
import com.youyu.common.entity.BaseEntity;
import com.youyu.common.exception.BizException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByName;
import static com.youyu.cardequity.common.base.util.StatusAndStrategyNumUtil.getNumber;
import static com.youyu.cardequity.common.base.util.UuidUtil.uuid4NoRail;
import static com.youyu.cardequity.payment.biz.enums.PayChannelStateEnum.getPayChannelStateEnum;
import static com.youyu.cardequity.payment.enums.PaymentResultCodeEnum.PAYMENT_CHANNEL_STATUS_IS_ABNORMAL;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work PayChannelInfo支付渠道
 */
@Getter
@Setter
@Table(name = "TB_PAY_CHANNEL_INFO")
public class PayChannelInfo extends BaseEntity<String> {

    /**
     * 渠道号:手动定义，不重复即可
     */
    @Id
    @Column(name = "CHANNEL_NO")
    private String channelNo;

    /**
     * 渠道名称:如"工行批扣通道"
     */
    @Column(name = "CHANNEL_NAME")
    private String channelName;

    /**
     * 支付机构号:关联Tb_PayOrgInfo表:PayOrgNo支付机构号
     */
    @Column(name = "PAY_ORG_NO")
    private String payOrgNo;

    /**
     * 支付接口类型:字典100001
     */
    @Column(name = "PAY_INTERFACE_TYPE")
    private String payInterfaceType;

    /**
     * 支付模式:字典100002
     */
    @Column(name = "PAY_MODE")
    private String payMode;

    /**
     * 校验方式:字典100003
     */
    @Column(name = "CHECK_LEVEL")
    private String checkLevel;

    /**
     * 连续调用失败笔数:自动统计的，当该值大于系统配置参数时将Tb_PayChannelInfo.State置为2；每次调用成功后清0
     */
    @Column(name = "CONTINUITY_FAIL_NUM")
    private Integer continuityFailNum;

    /**
     * 支持的证件类型:多个用,号分割
     */
    @Column(name = "CERTIFICATE_TYPES")
    private String certificateTypes;

    /**
     * 状态:0-正常、1-维护中、2-自动检测异常、3-暂停
     */
    @Column(name = "STATE")
    private String state;

    /**
     * 签约顺序:越小优先级越高:优先签约
     */
    @Column(name = "SIGN_ORDER")
    private Integer signOrder;

    /**
     * 备注:
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 支付工厂
     */
    @Column(name = "PAY_LOG_FACTORY")
    private PayLogFactory payLogFactory;

    /**
     * 支付策略
     */
    @Column(name = "PAY_LOG_STRATEGY")
    private PayLogStrategy payLogStrategy;

    /**
     * 退款工厂
     */
    @Column(name = "PAY_TRADE_REFUND_FACTORY")
    private PayTradeRefundFactory payTradeRefundFactory;

    /**
     * 退款策略
     */
    @Column(name = "PAY_TRADE_REFUND_STRATEGY")
    private PayTradeRefundStrategy payTradeRefundStrategy;

    /**
     * 对账工厂
     */
    @Column(name = "PAY_CHECK_FILE_DEATAIL_FACTORY")
    private PayCheckFileDeatailFactory payCheckFileDeatailFactory;

    /**
     * 对账策略
     */
    @Column(name = "PAY_CHECK_FILE_DEATAIL_STRATEGY")
    private PayCheckFileDeatailStrategy payCheckFileDeatailStrategy;

    public PayChannelInfo() {
    }

    public PayChannelInfo(PayChannelInfoDto payChannelInfoDto) {
        this.channelNo = uuid4NoRail();
        this.channelName = payChannelInfoDto.getChannelName();
        this.payOrgNo = payChannelInfoDto.getPayOrgNo();
        this.payInterfaceType = payChannelInfoDto.getPayInterfaceType();
        this.payMode = payChannelInfoDto.getPayMode();
        this.checkLevel = payChannelInfoDto.getCheckLevel();
        this.continuityFailNum = payChannelInfoDto.getContinuityFailNum();
        this.certificateTypes = payChannelInfoDto.getCertificateTypes();
        this.state = payChannelInfoDto.getState();
        this.signOrder = payChannelInfoDto.getSignOrder();
        this.remark = payChannelInfoDto.getRemark();
        this.payLogFactory = (PayLogFactory) getBeanByName(PayLogFactory.class.getSimpleName() + payChannelInfoDto.getPayLogFactoryNo());
        this.payLogStrategy = (PayLogStrategy) getBeanByName(PayLogStrategy.class.getSimpleName() + payChannelInfoDto.getPayLogStrategyNo());
        this.payTradeRefundFactory = (PayTradeRefundFactory) getBeanByName(PayTradeRefundFactory.class.getSimpleName() + payChannelInfoDto.getPayLogFactoryNo());
        this.payTradeRefundStrategy = (PayTradeRefundStrategy) getBeanByName(PayTradeRefundStrategy.class.getSimpleName() + payChannelInfoDto.getPayLogFactoryNo());
        this.payCheckFileDeatailFactory = (PayCheckFileDeatailFactory) getBeanByName(PayCheckFileDeatailFactory.class.getSimpleName() + payChannelInfoDto.getPayCheckFileDeatailFactoryNo());
        this.payCheckFileDeatailStrategy = (PayCheckFileDeatailStrategy) getBeanByName(PayCheckFileDeatailStrategy.class.getSimpleName() + payChannelInfoDto.getPayCheckFileDeatailStrategyNo());
    }

    public PayLog createPayLogAndPay(PayLogDto payLogDto) {
        checkState();
        PayLog payLog = payLogFactory.createPayLog(payLogDto);
        payLogStrategy.executePay(payLog);
        return payLog;
    }

    public PayTradeRefund createPayRefundAndRefund(PayTradeRefundDto tradeRefundApplyDto, PayLog payLog) {
        checkState();
        PayTradeRefund payRefund = payTradeRefundFactory.createPayRefund(tradeRefundApplyDto, payLog);
        payTradeRefundStrategy.executePayTradeRefund(payRefund);
        return payRefund;
    }

    private void checkState() {
        PayChannelStateEnum payChannelStateEnum = getPayChannelStateEnum(state);
        if (!payChannelStateEnum.canCreatePayLog()) {
            throw new BizException(PAYMENT_CHANNEL_STATUS_IS_ABNORMAL.getCode(), PAYMENT_CHANNEL_STATUS_IS_ABNORMAL.getFormatDesc(channelNo, state));
        }
    }

    public void downloadBill(PayCheckFileDeatailDto payCheckFileDeatailDto) {
        checkState();
        payCheckFileDeatailFactory.createPayCheckFileDeatail(payCheckFileDeatailDto);
    }

    public void doBill2Trade(PayCheckFileDeatail payCheckFileDeatail, TradeOrder tradeOrder) {
        checkState();
        payCheckFileDeatailStrategy.doBill2Trade(payCheckFileDeatail, tradeOrder);
    }

    public void doBill2TradeRefund(PayCheckFileDeatail payCheckFileDeatail, TradeOrder tradeOrder) {
        checkState();
        payCheckFileDeatailStrategy.doBill2TradeRefund(payCheckFileDeatail, tradeOrder);
    }

    public void doTrade2Bill(TradeOrder tradeOrder, PayCheckFileDeatail payCheckFileDeatail) {
        doBill2Trade(payCheckFileDeatail, tradeOrder);
    }

    public void doTrade2BillRefund(TradeOrder tradeOrder, PayCheckFileDeatail payCheckFileDeatail) {
        doBill2TradeRefund(payCheckFileDeatail, tradeOrder);
    }

    public void doTrade2BillNotFile(TradeOrder tradeOrder) {
        checkState();
        payCheckFileDeatailStrategy.doTrade2BillNotFile(tradeOrder);
    }

    public void doTrade2BillRefundNotFile(TradeOrder tradeOrder) {
        checkState();
        payCheckFileDeatailStrategy.doTrade2BillRefundNotFile(tradeOrder);
    }

    public String getPayLogFactoryNo() {
        return getNumber(this.payLogFactory);
    }

    public String getPayStrategyNo() {
        return getNumber(this.payLogStrategy);
    }

    public String getPayTradeRefundFactoryNo() {
        return getNumber(this.payTradeRefundFactory);
    }

    public String getPayTradeRefundStrategyNo() {
        return getNumber(this.payTradeRefundStrategy);
    }

    public String getPayCheckFileDeatailFactoryNo() {
        return getNumber(this.payCheckFileDeatailFactory);
    }

    public String getPayCheckFileDeatailStrategyNo() {
        return getNumber(this.payCheckFileDeatailStrategy);
    }

    @Override
    public String getId() {
        return channelNo;
    }

    @Override
    public void setId(String channelNo) {
        this.channelNo = channelNo;
    }
}
