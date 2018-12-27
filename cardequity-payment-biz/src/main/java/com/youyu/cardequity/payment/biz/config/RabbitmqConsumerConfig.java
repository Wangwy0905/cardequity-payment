package com.youyu.cardequity.payment.biz.config;

import com.youyu.cardequity.payment.biz.service.PayCheckFileDeatailService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.amqp.core.AcknowledgeMode.MANUAL;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Rabbitmq consumer
 */
@Configuration
public class RabbitmqConsumerConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    @Qualifier("payAsyncMessageQueue")
    private Queue tradeOrderMessageQueue;

    @Autowired
    private PayCheckFileDeatailService payCheckFileDeatailService;

    /**
     * 消费:交易订单同步消息数据
     *
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer tradeOrderMessageContainer() {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        simpleMessageListenerContainer.setQueues(tradeOrderMessageQueue);
        simpleMessageListenerContainer.setExposeListenerChannel(true);
        simpleMessageListenerContainer.setMaxConcurrentConsumers(4);
        simpleMessageListenerContainer.setConcurrentConsumers(16);
        simpleMessageListenerContainer.setAcknowledgeMode(MANUAL);
        simpleMessageListenerContainer.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            byte[] body = message.getBody();
            try {
                payCheckFileDeatailService.syncTradeOrderMessage(new String(body));
                //确认消息成功消费,删除queue里面的消息
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (Exception e) {
                //拒绝消费消息,消息将会重新投递到别的Consumer
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            }
        });
        return simpleMessageListenerContainer;
    }
}
