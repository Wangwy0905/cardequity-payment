package com.youyu.cardequity.payment.biz.component.status.paytraderefund;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;
import static com.youyu.cardequity.payment.dto.PayTradeRefundResponseDto.STATUS_SUCC;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 退款状态:退款成功
 */
@StatusAndStrategyNum(superClass = PayTradeRefundStatus.class, number = STATUS_SUCC, describe = "退款成功")
@Component
public class PayTradeRefundStatus4RefundSucc extends PayTradeRefundStatus {

    @Override
    public PayTradeRefundStatus refundSucc() {
        return getBeanByClass(PayTradeRefundStatus4RefundSucc.class);
    }

    @Override
    public PayTradeRefundStatus refundFail() {
        return getBeanByClass(PayTradeRefundStatus4RefundFail.class);
    }

    @Override
    public Boolean refundFlag() {
        return true;
    }
}
