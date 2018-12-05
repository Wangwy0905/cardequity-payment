package com.youyu.cardequity.payment.biz;

import com.youyu.cardequity.common.spring.base.BaseSpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.youyu.middleground.communication.api", "com.youyu.middleground.storage.api"})
public class CardequityPaymentApplication extends BaseSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardequityPaymentApplication.class, args);
    }

}
