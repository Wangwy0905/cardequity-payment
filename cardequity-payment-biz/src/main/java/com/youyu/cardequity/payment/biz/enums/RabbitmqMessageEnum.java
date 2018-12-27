package com.youyu.cardequity.payment.biz.enums;

import com.youyu.cardequity.common.base.tuple2.Tuple2;
import com.youyu.cardequity.payment.biz.component.properties.RabbitmqProperties;
import lombok.Getter;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanByClass;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work rabbitmq 消息枚举
 */
@Getter
public enum RabbitmqMessageEnum {

    PAY_ASYNC_MESSAGE("0", "支付宝异步支付消息") {
        @Override
        public Tuple2<String, String> getExchangeRoutingKey() {
            RabbitmqProperties rabbitmqProperties = getBeanByClass(RabbitmqProperties.class);
            String exchange = rabbitmqProperties.getPayAsyncMessageExchange();
            String routingKey = rabbitmqProperties.getPayAsyncMessageRoutingKey();
            return new Tuple2<>(exchange, routingKey);
        }
    };

    private String code;

    private String msg;

    RabbitmqMessageEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public abstract Tuple2<String, String> getExchangeRoutingKey();
}
