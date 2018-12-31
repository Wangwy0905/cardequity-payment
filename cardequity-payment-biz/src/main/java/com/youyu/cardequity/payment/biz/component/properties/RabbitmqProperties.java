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
     * 支付异步消息通知:RoutingKey:建议路由key和绑定key一样,路由key:是用于发送消息到交换机上的。绑定key:是用于交换机和queue队列绑定的。
     */
    private String payAsyncMessageRoutingKey = "rabbit_pay_async_message_routing_key";


    /**
     * 交易订单消息通知:Queue
     */
    private String tradeOrderMessageQueue = "rabbit_trade_order_message_queue";


    /**
     * 支付宝盘后对账退款消息通知:Queue
     */
    private String payAfterRefundMessageQueue = "rabbit_pay_after_refund_message_queue";
    /**
     * 支付宝盘后对账退款消息通知:Exchange
     */
    private String payAfterRefundMessageExchange = "rabbit_pay_after_refund_message_exchange";
    /**
     * 支付宝盘后对账退款消息通知:RoutingKey:建议路由key和绑定key一样,路由key:是用于发送消息到交换机上的。绑定key:是用于交换机和queue队列绑定的。
     */
    private String payAfterRefundMessageRoutingKey = "rabbit_pay_after_refund_message_routing_key";


    /**
     * 支付宝盘后对账退款状态消息通知:Queue
     */
    private String payAfterRefundStatusMessageQueue = "rabbit_pay_after_refund_status_message_queue";
    /**
     * 支付宝盘后对账退款状态消息通知:Exchange
     */
    private String payAfterRefundStatusMessageExchange = "rabbit_pay_after_refund_status_message_exchange";
    /**
     * 支付宝盘后对账退款状态消息通知:RoutingKey:建议路由key和绑定key一样,路由key:是用于发送消息到交换机上的。绑定key:是用于交换机和queue队列绑定的。
     */
    private String payAfterRefundStatusMessageRoutingKey = "rabbit_pay_after_refund_status_message_routing_key";


    /**
     * 支付宝盘后对账支付失败状态且非日切的消息通知:Queue
     */
    private String payAfterPayFailNotDayCutMessageQueue = "rabbit_pay_after_refund_status_message_queue";
    /**
     * 支付宝盘后对账支付失败状态且非日切的消息通知:Exchange
     */
    private String payAfterPayFailNotDayCutMessageExchange = "rabbit_pay_after_refund_status_message_exchange";
    /**
     * 支付宝盘后对账支付失败状态且非日切的消息通知:RoutingKey:建议路由key和绑定key一样,路由key:是用于发送消息到交换机上的。绑定key:是用于交换机和queue队列绑定的。
     */
    private String payAfterPayFailNotDayCutMessageRoutingKey = "rabbit_pay_after_refund_status_message_routing_key";
}
