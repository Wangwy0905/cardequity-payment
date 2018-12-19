package com.youyu.cardequity.payment.biz.component.status.paylog;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;
import static com.youyu.cardequity.payment.dto.PayLogResponseDto.STATUS_PAYMENT_QUEUING;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 支付状态:支付排队中
 */
@StatusAndStrategyNum(superClass = PayLogStatus.class, number = STATUS_PAYMENT_QUEUING, describe = "支付排队中")
@Component
public class PayLogStatus4PaymentQueuing extends PayLogStatus {

    @Override
    public PayLogStatus paymentQueuing() {
        return getBeanByClass(PayLogStatus4Paymenting.class);
    }

    @Override
    public boolean canPayTradeQuery() {
        return false;
    }
}
