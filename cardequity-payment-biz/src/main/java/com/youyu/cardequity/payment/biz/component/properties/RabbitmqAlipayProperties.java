package com.youyu.cardequity.payment.biz.component.properties;

import com.youyu.cardequity.common.base.annotation.SpringBean;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Alipay rabbitmq 属性装载Bean
 */
@SpringBean
@Setter
@Getter
@ConfigurationProperties(value = "rabbit.alipay")
@Component
public class RabbitmqAlipayProperties {

    /**
     * 支付宝支付异步消息通知:Queue
     */
    private String asyncMessageQueue = "rabbit_alipay_async_message_queue";
    /**
     * 支付宝支付异步消息通知:Exchange
     */
    private String asyncMessageExchange = "rabbit_alipay_async_message_exchange";
    /**
     * 支付宝支付异步消息通知:RoutingKey
     */
    private String asyncMessageRoutingKey = "rabbit_alipay_async_message_routing_key";
}
