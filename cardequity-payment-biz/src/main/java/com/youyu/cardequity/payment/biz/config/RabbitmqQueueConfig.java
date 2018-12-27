package com.youyu.cardequity.payment.biz.config;

import com.youyu.cardequity.payment.biz.component.properties.RabbitmqAlipayProperties;
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
    private RabbitmqAlipayProperties rabbitmqAlipayProperties;

    /**
     * 支付宝支付异步通知消息:Queue
     *
     * @return
     */
    @Bean("alipayAsyncMessageQueue")
    public Queue alipayAsyncMessageQueue() {
        return new Queue(rabbitmqAlipayProperties.getPayAsyncMessageQueue(), true);
    }


}
