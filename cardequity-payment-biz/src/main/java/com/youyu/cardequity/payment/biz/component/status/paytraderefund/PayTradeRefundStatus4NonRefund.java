package com.youyu.cardequity.payment.biz.component.status.paytraderefund;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;
import static com.youyu.cardequity.payment.dto.PayTradeRefundResponseDto.STATUS_NON_REFUND;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 退款状态:未退款
 */
@StatusAndStrategyNum(superClass = PayTradeRefundStatus.class, number = STATUS_NON_REFUND, describe = "未退款")
@Component
public class PayTradeRefundStatus4NonRefund extends PayTradeRefundStatus {

    @Override
    public PayTradeRefundStatus refunding() {
        return getBeanByClass(PayTradeRefundStatus4Refunding.class);
    }
}
