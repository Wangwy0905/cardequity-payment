package com.youyu.cardequity.payment.biz.component.strategy.paycheckfiledeatail;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.dal.entity.PayCheckFileDeatail;
import com.youyu.cardequity.payment.biz.dal.entity.TradeOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 对账策略:支付宝
 */
@Slf4j
@StatusAndStrategyNum(superClass = PayCheckFileDeatailStrategy.class, number = "1", describe = "支付宝对账策略")
@Component
public class PayCheckFileDeatailStrategy4Alipay extends PayCheckFileDeatailStrategy {

    @Override
    public void doBill2Trade(PayCheckFileDeatail payCheckFileDeatail, TradeOrder tradeOrder) {
        // TODO: 2018/12/28
        // 状态一致: 正常
        // 状态不一致: 退款 :通知交易系统
    }

    @Override
    public void doBill2TradeRefund(PayCheckFileDeatail payCheckFileDeatail, TradeOrder tradeOrder) {
        // TODO: 2018/12/28
        // 退款状态一致: 正常
        // 状态不一致: 通知交易修改成退款状态即可
    }

    @Override
    public void doTrade2Bill(TradeOrder tradeOrder, PayCheckFileDeatail payCheckFileDeatail) {
        // TODO: 2018/12/28
        // 状态一致: 正常
        // 状态不一致: 退款 :通知交易系统
    }

    @Override
    public void doTrade2BillRefund(TradeOrder tradeOrder, PayCheckFileDeatail payCheckFileDeatail) {
        // TODO: 2018/12/28
        // 退款状态一致: 正常
        // 退款状态不一致: 通知交易修改成退款状态即可
    }
}
