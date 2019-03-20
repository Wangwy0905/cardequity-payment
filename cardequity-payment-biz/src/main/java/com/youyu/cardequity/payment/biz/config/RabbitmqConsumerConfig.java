package com.youyu.cardequity.payment.biz.config;

import com.youyu.cardequity.common.spring.mq.BaseRabbitConsumer;
import com.youyu.cardequity.common.spring.service.RabbitConsumerService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Rabbitmq consumer
 */
@Configuration
public class RabbitmqConsumerConfig extends BaseRabbitConsumer {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    @Qualifier("tradeOrderMessageQueue")
    private Queue tradeOrderMessageQueue;

    @Autowired
    @Qualifier("tradeOrderService")
    private RabbitConsumerService rabbitConsumerService;

    @Override
    @Bean("tradeOrderConsumerMessageContainer")
    public SimpleMessageListenerContainer consumerMessageContainer(ConnectionFactory connectionFactory) {
        return super.consumerMessageContainer(connectionFactory);
    }

    @Override
    protected RabbitConsumerService getRabbitConsumerService() {
        return rabbitConsumerService;
    }

    @Override
    protected Queue getQueue() {
        return tradeOrderMessageQueue;
    }
}
