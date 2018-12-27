package com.youyu.cardequity.payment.biz.config;

import com.youyu.cardequity.payment.biz.component.properties.RabbitmqProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.amqp.core.BindingBuilder.bind;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月21日 下午15:54:27
 * @work Rabbitmq Binding Config 绑定配置
 */
@Configuration
public class RabbitmqBindingConfig {

    @Autowired
    private RabbitmqProperties rabbitmqAlipayProperties;

    @Autowired
    @Qualifier("payAsyncMessageQueue")
    private Queue payAsyncMessageQueue;

    @Autowired
    @Qualifier("payAsyncMessageExchange")
    private DirectExchange payAsyncMessageExchange;

    /**
     * 支付宝支付异步通知消息:Binding
     *
     * @return
     */
    @Bean
    public Binding alipayAsyncMessageBinding() {
        return bind(payAsyncMessageQueue).to(payAsyncMessageExchange).with(rabbitmqAlipayProperties.getPayAsyncMessageRoutingKey());
    }
}
