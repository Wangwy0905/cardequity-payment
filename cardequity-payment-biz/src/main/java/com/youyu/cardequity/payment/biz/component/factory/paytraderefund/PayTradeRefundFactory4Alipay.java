package com.youyu.cardequity.payment.biz.component.factory.paytraderefund;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayTradeRefund;
import com.youyu.cardequity.payment.biz.dal.entity.PayTradeRefund4Alipay;
import com.youyu.cardequity.payment.dto.TradeRefundApplyDto;
import org.springframework.stereotype.Component;

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
    public PayTradeRefund createPayTradeRefund(TradeRefundApplyDto tradeRefundApplyDto, PayLog payLog) {
        return new PayTradeRefund4Alipay(tradeRefundApplyDto, payLog);
    }
}
