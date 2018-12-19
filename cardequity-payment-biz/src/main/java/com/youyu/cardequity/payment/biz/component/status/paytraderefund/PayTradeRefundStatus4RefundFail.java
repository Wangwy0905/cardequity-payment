package com.youyu.cardequity.payment.biz.component.status.paytraderefund;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;
import static com.youyu.cardequity.payment.dto.PayTradeRefundResponseDto.STATUS_FAIL;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 退款状态:退款失败
 */
@StatusAndStrategyNum(superClass = PayTradeRefundStatus.class, number = STATUS_FAIL, describe = "退款失败")
@Component
public class PayTradeRefundStatus4RefundFail extends PayTradeRefundStatus {

    @Override
    public PayTradeRefundStatus refundSucc() {
        return getBeanByClass(PayTradeRefundStatus4RefundSucc.class);
    }

    @Override
    public PayTradeRefundStatus refundFail() {
        return getBeanByClass(PayTradeRefundStatus4RefundFail.class);
    }

    @Override
    public PayTradeRefundStatus refunding() {
        return getBeanByClass(PayTradeRefundStatus4Refunding.class);
    }
}
