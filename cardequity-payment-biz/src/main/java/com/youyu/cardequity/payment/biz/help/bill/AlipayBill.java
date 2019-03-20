package com.youyu.cardequity.payment.biz.help.bill;

import com.csvreader.CsvReader;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serializable;

import static org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace;
import static org.apache.commons.lang3.StringUtils.trim;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付宝对账单
 */
@Slf4j
@Setter
@Getter
public class AlipayBill implements Serializable {

    private static final long serialVersionUID = -428912097340252770L;

    /**
     * 字段号0
     * 支付宝交易号
     */
    private String tradeNo;

    /**
     * 字段号1
     * 商户订单号
     */
    private String orderNo;

    /**
     * 字段号2
     * 业务类型
     */
    private String businType;

    /**
     * 字段号3
     * 商品名称
     */
    private String commodityName;

    /**
     * 字段号4
     * 创建时间
     */
    private String createTime;

    /**
     * 字段号5
     * 完成时间
     */
    private String completeTime;

    /**
     * 字段号6
     * 门店编号
     */
    private String shopSerialNo;

    /**
     * 字段号7
     * 门店名称
     */
    private String shopName;

    /**
     * 字段号8
     * 操作员
     */
    private String operator;

    /**
     * 字段号9
     * 终端号
     */
    private String terminalNo;

    /**
     * 字段号10
     * 对方账户
     */
    private String buyAccount;

    /**
     * 字段号11
     * 订单金额（元）
     */
    private String orderAmount;

    /**
     * 字段号12
     * 商家实收（元）
     */
    private String MerchantsPaidIn;

    /**
     * 字段号13
     * 支付宝红包（元）
     */
    private String alipayRedEnvelope;

    /**
     * 字段号14
     * 集分宝（元）
     */
    private String setPointsTreasure;

    /**
     * 字段号15
     * 支付宝优惠（元）
     */
    private String alipayPreferential;

    /**
     * 字段号16
     * 商家优惠（元）
     */
    private String merchantsPreferential;

    /**
     * 字段号17
     * 券核销金额（元）
     */
    private String couponCancelAmount;

    /**
     * 字段号18
     * 券名称
     */
    private String couponName;

    /**
     * 字段号19
     * 商家红包消费金额（元）
     */
    private String redEnvelopeConsumptionAmount;

    /**
     * 字段号20
     * 卡消费金额（元）
     */
    private String cardConsumptionAmount;

    /**
     * 字段号21
     * 退款批次号/请求号
     */
    private String returnBatchNo;

    /**
     * 字段号22
     * 服务费（元）
     */
    private String serviceCharge;

    /**
     * 字段号23
     * 分润（元）
     */
    private String shareProfit;

    /**
     * 字段号24
     * 备注
     */
    private String remark;

    public AlipayBill() {

    }

    public AlipayBill(CsvReader csvReader) {
        try {
            this.tradeNo = trim(csvReader.get(0));
            this.orderNo = trim(csvReader.get(1));
            this.businType = trim(csvReader.get(2));
            this.commodityName = trim(csvReader.get(3));
            this.createTime = trim(csvReader.get(4));
            this.completeTime = trim(csvReader.get(5));
            this.shopSerialNo = trim(csvReader.get(6));
            this.shopName = trim(csvReader.get(7));
            this.operator = trim(csvReader.get(8));
            this.terminalNo = trim(csvReader.get(9));
            this.buyAccount = trim(csvReader.get(10));
            this.orderAmount = trim(csvReader.get(11));
            this.MerchantsPaidIn = trim(csvReader.get(12));
            this.alipayRedEnvelope = trim(csvReader.get(13));
            this.setPointsTreasure = trim(csvReader.get(14));
            this.alipayPreferential = trim(csvReader.get(15));
            this.merchantsPreferential = trim(csvReader.get(16));
            this.couponCancelAmount = trim(csvReader.get(17));
            this.couponName = trim(csvReader.get(18));
            this.redEnvelopeConsumptionAmount = trim(csvReader.get(19));
            this.cardConsumptionAmount = trim(csvReader.get(20));
            this.returnBatchNo = trim(csvReader.get(21));
            this.serviceCharge = trim(csvReader.get(22));
            this.shareProfit = trim(csvReader.get(23));
            this.remark = trim(csvReader.get(24));
        } catch (IOException e) {
            log.error("支付宝对账单解析异常信息:[{}]", getFullStackTrace(e));
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
