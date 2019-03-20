package com.youyu.cardequity.payment.biz.component.factory.paytraderefund;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.dal.dao.PayTradeRefundMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayTradeRefund;
import com.youyu.cardequity.payment.biz.dal.entity.PayTradeRefund4Alipay;
import com.youyu.cardequity.payment.dto.PayTradeRefundDto;
import com.youyu.common.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.youyu.cardequity.common.base.util.MoneyUtil.gt;
import static com.youyu.cardequity.payment.enums.PaymentResultCodeEnum.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Alipay 退款工厂
 */
@StatusAndStrategyNum(superClass = PayTradeRefundFactory.class, number = "1", describe = "支付宝退款工厂")
@Component
public class PayTradeRefundFactory4Alipay extends PayTradeRefundFactory {

    @Autowired
    private PayTradeRefundMapper payTradeRefundMapper;

    @Override
    public PayTradeRefund createPayRefund(PayTradeRefundDto tradeRefundApplyDto, PayLog payLog) {
        PayTradeRefund existPayTradeRefund = checkExistPayTradeRefund(tradeRefundApplyDto, payLog);
        if (nonNull(existPayTradeRefund)) {
            return existPayTradeRefund;
        }

        PayTradeRefund4Alipay payTradeRefund4Alipay = new PayTradeRefund4Alipay(tradeRefundApplyDto, payLog);
        payTradeRefundMapper.insertSelective(payTradeRefund4Alipay);
        return payTradeRefund4Alipay;
    }

    private PayTradeRefund checkExistPayTradeRefund(PayTradeRefundDto tradeRefundApplyDto, PayLog payLog) {
        if (!payLog.createPayRefund()) {
            throw new BizException(SUCCESS_ORDER_PAYMENT_CAN_REFUND);
        }

        BigDecimal refundAmount = tradeRefundApplyDto.getRefundAmount();
        BigDecimal occurBalance = payLog.getOccurBalance();
        if (gt(refundAmount, occurBalance)) {
            throw new BizException(REFUND_AMOUNT_CANNOT_GREATER_PAYMENT_AMOUNT);
        }

        PayTradeRefund payTradeRefund = payTradeRefundMapper.getByAppSheetSerialNoRefundNo(tradeRefundApplyDto.getAppSheetSerialNo(), tradeRefundApplyDto.getRefundNo());
        if (isNull(payTradeRefund)) {
            return null;
        }

        if (payTradeRefund.isRefundSucc()) {
            throw new BizException(REFUND_SUCC_CANNOT_REFUNDED);
        }

        return payTradeRefund;
    }
}
