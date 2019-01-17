package com.youyu.cardequity.payment.biz.dal.entity;

import com.youyu.cardequity.payment.biz.dal.dao.PayCheckFileDeatailMapper;
import com.youyu.cardequity.payment.biz.help.bill.AlipayBill;
import com.youyu.cardequity.payment.dto.PayCheckFileDeatailDto;
import com.youyu.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;
import static com.youyu.cardequity.common.base.util.MoneyUtil.string2BigDecimal;
import static com.youyu.cardequity.common.base.util.UuidUtil.uuid4NoRail;
import static com.youyu.cardequity.payment.dto.PayLogResponseDto.STATUS_PAYMENT_SUCC;
import static com.youyu.cardequity.payment.dto.PayTradeRefundResponseDto.STATUS_SUCC;
import static org.apache.commons.lang3.StringUtils.isNoneBlank;
import static org.apache.commons.lang3.StringUtils.replace;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 对账文件明细信息表
 */
@Getter
@Setter
@Table(name = "TB_PAY_CHECK_FILE_DEATAIL")
public class PayCheckFileDeatail extends BaseEntity<String> {

    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 三方流水号:文件中给到
     */
    @Column(name = "TRANCE_NO")
    private String tranceNo;

    /**
     * 渠道号:
     */
    @Column(name = "CHANNEL_NO")
    private String channelNo;

    /**
     * 对账单日期:
     */
    @Column(name = "CHECK_DATE")
    private String checkDate;

    /**
     * 文件编号:
     */
    @Column(name = "FILE_ID")
    private String fileId;

    /**
     * 申请时间:带时间格式：支付时银行记录的实际,格式不定，先存字符串
     */
    @Column(name = "APP_DATE")
    private String appDate;

    /**
     * 申请金额:
     */
    @Column(name = "APP_AMOUNT")
    private BigDecimal appAmount;

    /**
     * 支付状态:每个渠道定义不一样，需要解析后转义
     */
    @Column(name = "PAY_STATE")
    private String payState;

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
     * 文件名称:
     */
    @Column(name = "FILE_NAME")
    private String fileName;

    /**
     * 备注:
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 订单金额
     */
    @Column(name = "ORDER_AMOUNT")
    private BigDecimal orderAmount;

    /**
     * 退款批次号
     */
    @Column(name = "REFUND_BATCH_NO")
    private String refundBatchNo;

    /**
     * 退款状态:每个渠道定义不一样，需要解析后转义
     */
    @Column(name = "RETURN_STATUS")
    private String returnStatus;

    /**
     * 对账id
     */
    @Column(name = "PAY_CHECK_DEATAIL_ID")
    private String payCheckDeatailId;

    public PayCheckFileDeatail() {
        this.id = uuid4NoRail();
    }

    /**
     * data格式:param1,param2,param3...
     * 具体参数参考支付宝对账单:https://docs.open.alipay.com/204/106262/
     *
     * @param alipayBill
     * @param payCheckFileDeatailDto
     * @param fileName
     * @param businType
     */
    public PayCheckFileDeatail(AlipayBill alipayBill, PayCheckFileDeatailDto payCheckFileDeatailDto, String fileName, String businType) {
        this();
        this.tranceNo = alipayBill.getTradeNo();
        this.channelNo = payCheckFileDeatailDto.getChannelNo();
        this.checkDate = replace(payCheckFileDeatailDto.getBillDate(), "-", "");
        this.appDate = alipayBill.getCreateTime();
        this.appAmount = string2BigDecimal(alipayBill.getMerchantsPaidIn());
        this.payState = STATUS_PAYMENT_SUCC;
        this.appSheetSerialNo = alipayBill.getOrderNo();
        this.businType = businType;
        this.fileName = fileName;
        this.remark = alipayBill.getRemark();
        this.orderAmount = string2BigDecimal(alipayBill.getOrderAmount());
        String refundBatchNo = alipayBill.getReturnBatchNo();
        if (isNoneBlank(refundBatchNo)) {
            this.refundBatchNo = refundBatchNo;
            this.returnStatus = STATUS_SUCC;
        }
    }

    public PayCheckFileDeatail(PayCheckFileDeatail payCheckFileDeatail) {
        this.tranceNo = payCheckFileDeatail.getTranceNo();
        this.checkDate = payCheckFileDeatail.getCheckDate();
    }

    public void reconciliationed(PayCheckDeatail payCheckDeatail) {
        this.payCheckDeatailId = payCheckDeatail.getId();
        getBeanByClass(PayCheckFileDeatailMapper.class).updatePayCheckDeatailIdById(id, payCheckDeatailId);
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
