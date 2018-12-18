package com.youyu.cardequity.payment.biz.dal.entity;

import com.youyu.cardequity.payment.dto.TradeRefundApplyDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work alipay PayTradeRefund退款
 */
@Getter
@Setter
@Table(name = "TB_PAY_TRADE_REFUND")
public class PayRefund4Alipay extends PayRefund {

    /**
     * 支付宝支付宝交易号
     */
    @Column(name = "ALIPAY_TRADE_NO")
    private String alipayTradeNo;

    /**
     * 支付宝退款响应信息
     */
    @Column(name = "ALIPAY_REFUND_RESPONSE")
    private String alipayRefundResponse;

    public PayRefund4Alipay() {
    }

    public PayRefund4Alipay(TradeRefundApplyDto tradeRefundApplyDto, PayLog payLog) {
        super(tradeRefundApplyDto, payLog);
        this.alipayTradeNo = payLog.getThirdSerialNo();
        this.type = "1";// TODO: 2018/12/18
    }
}
