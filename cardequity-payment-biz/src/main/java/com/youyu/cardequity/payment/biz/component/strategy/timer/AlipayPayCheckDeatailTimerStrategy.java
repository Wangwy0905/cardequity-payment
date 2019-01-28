package com.youyu.cardequity.payment.biz.component.strategy.timer;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.service.PayCheckDeatailService;
import com.youyu.cardequity.payment.dto.PayCheckDeatailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付宝对账定时对账策略
 */
@StatusAndStrategyNum(superClass = TimerStrategy.class, number = "300", describe = "支付宝对账定时对账策略")
@Component
public class AlipayPayCheckDeatailTimerStrategy extends TimerStrategy {

    @Autowired
    private PayCheckDeatailService payCheckDeatailService;

    @Override
    public void execute() {
        PayCheckDeatailDto payCheckDeatailDto = new PayCheckDeatailDto();
        payCheckDeatailService.reconciliation(payCheckDeatailDto, payCheckDeatailService);
    }
}
