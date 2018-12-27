package com.youyu.cardequity.payment.biz.component.rabbitmq;

import com.youyu.cardequity.common.base.tuple2.Tuple2;
import com.youyu.cardequity.payment.biz.enums.RabbitmqMessageEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

import static com.youyu.cardequity.common.base.util.UuidUtil.uuid4NoRail;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Rabbitmq sender
 */
@Slf4j
@Component
public class RabbitmqSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private RabbitTemplate rabbitTemplate;

    public RabbitmqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
        this.rabbitTemplate.setReturnCallback(this);
    }

    public void sendMessage(String message, RabbitmqMessageEnum rabbitmqMessageEnum) {
        Tuple2<String, String> tuple2 = rabbitmqMessageEnum.getExchangeRoutingKey();
        rabbitTemplate.convertAndSend(tuple2.a, tuple2.b, message, new CorrelationData(uuid4NoRail()));
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("消息id:[{}]发送Exchange成功!", correlationData.getId());
        } else {
            log.info("消息id:[{}]发送Exchange失败以及异常信息:[{}]!", correlationData.getId(), cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息:[{}]进入Exchange交换机成功,但是没有进入Queue队列,消费失败!", message.getBody());
    }
}
