package com.youyu.cardequity.payment.biz.component.factory.paytraderefund;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayRefund;
import com.youyu.cardequity.payment.biz.dal.entity.PayRefund4Alipay;
import com.youyu.cardequity.payment.dto.TradeRefundApplyDto;
import com.youyu.common.exception.BizException;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.payment.enums.PaymentResultCodeEnum.SUCCESS_ORDER_PAYMENT_CAN_REFUND;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Alipay 退款工厂
 */
@StatusAndStrategyNum(superClass = PayTradeRefundFactory.class, number = "1", describe = "支付宝退款工厂")
@Component
public class PayTradeRefundFactory4Alipay extends PayTradeRefundFactory {

    @Override
    public PayRefund createPayRefund(TradeRefundApplyDto tradeRefundApplyDto, PayLog payLog) {
        check(payLog);
        return new PayRefund4Alipay(tradeRefundApplyDto, payLog);
    }

    private void check(PayLog payLog) {
        // TODO: 2018/12/18

        if (!payLog.createPayRefund()) {
            throw new BizException(SUCCESS_ORDER_PAYMENT_CAN_REFUND);
        }
    }
}
