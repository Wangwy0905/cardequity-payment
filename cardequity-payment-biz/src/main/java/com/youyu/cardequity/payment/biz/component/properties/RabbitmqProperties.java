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
 * @work rabbitmq 属性装载Bean
 */
@SpringBean
@Setter
@Getter
@ConfigurationProperties(value = "rabbit")
@Component
public class RabbitmqProperties {

    /**
     * 支付异步消息通知:Queue
     */
    private String payAsyncMessageQueue = "rabbit_pay_async_message_queue";
    /**
     * 支付异步消息通知:Exchange
     */
    private String payAsyncMessageExchange = "rabbit_pay_async_message_exchange";
    /**
     * 支付异步消息通知:RoutingKey
     */
    private String payAsyncMessageRoutingKey = "rabbit_pay_async_message_routing_key";

    /**
     * 交易订单消息通知:Queue
     */
    private String tradeOrderMessageQueue = "rabbit_trade_order_message_queue";
}
