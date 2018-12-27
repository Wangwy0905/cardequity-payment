package com.youyu.cardequity.payment.biz.config;

import com.youyu.cardequity.payment.biz.component.properties.RabbitmqProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月21日 下午15:54:27
 * @work Rabbitmq Queue Config 队列配置
 */
@Configuration
public class RabbitmqQueueConfig {

    @Autowired
    private RabbitmqProperties rabbitmqProperties;

    /**
     * 支付异步通知消息:Queue
     *
     * @return
     */
    @Bean("payAsyncMessageQueue")
    public Queue payAsyncMessageQueue() {
        return new Queue(rabbitmqProperties.getPayAsyncMessageQueue(), true);
    }

    /**
     * 交易订单消息通知:Queue
     *
     * @return
     */
    @Bean("payAsyncMessageQueue")
    public Queue tradeOrderMessageQueue() {
        return new Queue(rabbitmqProperties.getTradeOrderMessageQueue(), true);
    }

}
