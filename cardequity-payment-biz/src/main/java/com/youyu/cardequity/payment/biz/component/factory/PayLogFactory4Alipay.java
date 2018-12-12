package com.youyu.cardequity.payment.biz.component.factory;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog;
import com.youyu.cardequity.payment.biz.dal.entity.PayLog4Alipay;
import com.youyu.cardequity.payment.dto.PayLogDto;
import org.springframework.stereotype.Component;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Alipay Pay支付工厂
 */
@StatusAndStrategyNum(superClass = PayLogFactory.class, number = "1", describe = "支付宝支付创建工厂")
@Component
public class PayLogFactory4Alipay extends PayLogFactory {

    @Override
    public PayLog create(PayLogDto payLogDto) {
        return new PayLog4Alipay(payLogDto);
    }
}
