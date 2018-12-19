package com.youyu.cardequity.payment.biz.component.status.paylog;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;
import static com.youyu.cardequity.payment.dto.PayLogResponseDto.STATUS_NON_PAYMENT;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 支付状态:未支付
 */
@StatusAndStrategyNum(superClass = PayLogStatus.class, number = STATUS_NON_PAYMENT, describe = "未支付")
@Component
public class PayLogStatus4NonPayment extends PayLogStatus {

    @Override
    public PayLogStatus paymentQueuing() {
        return getBeanByClass(PayLogStatus4NonPayment.class);
    }

    @Override
    public PayLogStatus paymenting() {
        return getBeanByClass(PayLogStatus4Paymenting.class);
    }

    @Override
    public boolean canPayTradeQuery() {
        return false;
    }
}
