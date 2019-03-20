package com.youyu.cardequity.payment.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付系统基础应用启动类
 */
@SpringCloudApplication
@MapperScan("com.youyu.cardequity.payment.biz.dal.dao")
public class CardequityPaymentApplication {

    /**
     * 主启动方法
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(CardequityPaymentApplication.class, args);
    }

}
