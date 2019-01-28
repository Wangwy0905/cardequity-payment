package com.youyu.cardequity.payment.biz.component.strategy.timer;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付定时查询策略
 */
@StatusAndStrategyNum(superClass = TimerStrategy.class, number = "100", describe = "支付定时查询策略")
@Component
public class TimeTradeQueryTimerStrategy extends TimerStrategy {

    @Autowired
    private PayLogService payLogService;

    @Override
    public void execute() {
        payLogService.timeTradeQuery();
    }
}
