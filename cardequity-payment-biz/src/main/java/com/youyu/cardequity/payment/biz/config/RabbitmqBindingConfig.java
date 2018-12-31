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
    private RabbitmqProperties rabbitmqProperties;

    @Autowired
    @Qualifier("payAsyncMessageQueue")
    private Queue payAsyncMessageQueue;

    @Autowired
    @Qualifier("payAsyncMessageExchange")
    private DirectExchange payAsyncMessageExchange;

    /**
     * 支付异步通知消息:Binding
     *
     * @return
     */
    @Bean
    public Binding payAsyncMessageBinding() {
        return bind(payAsyncMessageQueue).to(payAsyncMessageExchange).with(rabbitmqProperties.getPayAsyncMessageRoutingKey());
    }

    @Autowired
    @Qualifier("payAfterRefundMessageQueue")
    private Queue payAfterRefundMessageQueue;

    @Autowired
    @Qualifier("payAfterRefundMessageExchange")
    private DirectExchange payAfterRefundMessageExchange;

    /**
     * 支付盘后对账退款消息通知:Binding
     *
     * @return
     */
    @Bean
    public Binding payAfterRefundMessageBinding() {
        return bind(payAfterRefundMessageQueue).to(payAfterRefundMessageExchange).with(rabbitmqProperties.getPayAfterRefundMessageRoutingKey());
    }

    @Autowired
    @Qualifier("payAfterRefundStatusMessageQueue")
    private Queue payAfterRefundStatusMessageQueue;

    @Autowired
    @Qualifier("payAfterRefundStatusMessageExchange")
    private DirectExchange payAfterRefundStatusMessageExchange;

    /**
     * 支付盘后对账退款状态消息通知:Binding
     *
     * @return
     */
    @Bean
    public Binding payAfterRefundStatusMessageBinding() {
        return bind(payAfterRefundStatusMessageQueue).to(payAfterRefundStatusMessageExchange).with(rabbitmqProperties.getPayAfterRefundStatusMessageRoutingKey());
    }

    @Autowired
    @Qualifier("payAfterPayFailNotDayCutMessageQueue")
    private Queue payAfterPayFailNotDayCutMessageQueue;

    @Autowired
    @Qualifier("payAfterPayFailNotDayCutMessageExchange")
    private DirectExchange payAfterPayFailNotDayCutMessageExchange;

    /**
     * 支付宝盘后对账支付失败状态且非日切的消息通知:Binding
     *
     * @return
     */
    @Bean
    public Binding payAfterPayFailNotDayCutMessageBinding() {
        return bind(payAfterPayFailNotDayCutMessageQueue).to(payAfterPayFailNotDayCutMessageExchange).with(rabbitmqProperties.getPayAfterPayFailNotDayCutMessageRoutingKey());
    }
}
