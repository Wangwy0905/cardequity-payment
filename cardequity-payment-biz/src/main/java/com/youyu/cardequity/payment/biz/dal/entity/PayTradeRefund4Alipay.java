package com.youyu.cardequity.payment.biz.dal.entity;

import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.youyu.cardequity.payment.biz.component.command.paylog.PayLogCommond4AlipayTradeFastpayRefundQuery;
import com.youyu.cardequity.payment.dto.PayTradeRefundDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

import static com.alibaba.fastjson.JSON.toJSONString;
import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;
import static com.youyu.cardequity.common.base.util.MoneyUtil.string2BigDecimal;
import static com.youyu.cardequity.common.base.util.StringUtil.eq;
import static com.youyu.cardequity.payment.biz.help.constant.AlipayConstant.ALIPAY_FUND_CHANGE_Y;
import static com.youyu.cardequity.payment.biz.help.constant.BusinessConstant.RETURN_TYPE_ALIPAY;
import static org.apache.commons.lang3.StringUtils.isNoneBlank;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work alipay PayTradeRefund退款
 */
@Getter
@Setter
@Table(name = "TB_PAY_TRADE_REFUND")
public class PayTradeRefund4Alipay extends PayTradeRefund {

    /**
     * 支付宝交易号
     */
    @Column(name = "ALIPAY_TRADE_NO")
    private String alipayTradeNo;

    /**
     * 支付宝退款响应信息
     */
    @Column(name = "ALIPAY_REFUND_RESPONSE")
    private String alipayRefundResponse;

    /**
     * 支付宝查询退款响应信息
     */
    @Column(name = "ALIPAY_REFUND_QUERY_RESPONSE")
    private String alipayRefundQueryResponse;

    public PayTradeRefund4Alipay() {
        super();
    }

    public PayTradeRefund4Alipay(PayTradeRefundDto tradeRefundApplyDto, PayLog payLog) {
        super(tradeRefundApplyDto, payLog);
        this.alipayTradeNo = payLog.getThirdSerialNo();
        this.type = RETURN_TYPE_ALIPAY;
    }

    public void callRefundSucc(AlipayTradeRefundResponse alipayTradeRefundResponse) {
        this.alipayRefundResponse = toJSONString(alipayTradeRefundResponse);

        boolean refundFlag = alipayTradeRefundResponse.isSuccess();
        if (!refundFlag) {
            this.status = status.refundFail();
            return;
        }

        this.status = eq(ALIPAY_FUND_CHANGE_Y, alipayTradeRefundResponse.getFundChange()) ? status.refundSucc() : status.refundFail();
        this.refundAmount = string2BigDecimal(alipayTradeRefundResponse.getRefundFee());
    }

    public void callRefundException(String remark) {
//        this.status = status.refundFail();
        this.remark = remark;
    }

    @Override
    public void getTradeRefund(PayLog payLog) {
        getBeanByClass(PayLogCommond4AlipayTradeFastpayRefundQuery.class).executeCmd(payLog, this);
    }

    public void callRefundQuerySucc(AlipayTradeFastpayRefundQueryResponse alipayTradeFastpayRefundQueryResponse) {
        boolean refundQueryFlag = alipayTradeFastpayRefundQueryResponse.isSuccess();
        String body = alipayTradeFastpayRefundQueryResponse.getBody();
        if (refundQueryFlag && isNoneBlank(body)) {
            this.status = status.refundSucc();
            this.alipayRefundQueryResponse = toJSONString(alipayTradeFastpayRefundQueryResponse);
            return;
        }

        this.status = status.refundFail();
    }

    public void callRefundQueryFail(String remark) {
        this.remark = remark;
    }
}
