package com.youyu.cardequity.payment.biz.config;

import com.youyu.cardequity.payment.biz.component.properties.RabbitmqAlipayProperties;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月21日 下午15:54:27
 * @work Rabbitmq Exchange Config 交换机配置
 */
@Configuration
public class RabbitmqExchangeConfig {

    @Autowired
    private RabbitmqAlipayProperties rabbitmqAlipayProperties;

    /**
     * 支付宝支付异步通知消息:Exchange
     *
     * @return
     */
    @Bean("alipayAsyncMessageExchange")
    public DirectExchange alipayAsyncMessageExchange() {
        return new DirectExchange(rabbitmqAlipayProperties.getPayAsyncMessageExchange(), true, false);
    }
}
