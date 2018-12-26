package com.youyu.cardequity.payment.biz.component.strategy.paycheckfiledeatail;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
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
}
