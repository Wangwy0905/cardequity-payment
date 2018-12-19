package com.youyu.cardequity.payment.biz.dal.entity;

import com.alipay.api.response.AlipayTradeRefundResponse;
import com.youyu.cardequity.payment.dto.PayTradeRefundDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

import static com.alibaba.fastjson.JSON.toJSONString;
import static com.youyu.cardequity.common.base.util.MoneyUtil.string2BigDecimal;
import static com.youyu.cardequity.payment.biz.help.constant.Constant.RETURN_TYPE_ALIPAY;

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

    public PayTradeRefund4Alipay() {
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

        this.status = status.refundSucc();
        this.refundAmount = string2BigDecimal(alipayTradeRefundResponse.getRefundFee());
    }

    public void callRefundFail(String remark) {
        this.status = status.refunding();
        this.remark = remark;
    }
}
