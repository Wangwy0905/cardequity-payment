package com.youyu.cardequity.payment.biz.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.youyu.cardequity.payment.biz.component.properties.AlipayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Bean配置类
 */
@Configuration
public class BeanConfig {

    @Autowired
    private AlipayProperties alipayProperties;

    /**
     * 支付宝支付客户端
     *
     * @return
     */
    @Bean
    public AlipayClient alipayClient() {
        AlipayClient alipayClient = new DefaultAlipayClient(
                alipayProperties.getUrl(),
                alipayProperties.getAppId(),
                alipayProperties.getAppPrivateKey(),
                alipayProperties.getFormat(),
                alipayProperties.getCharset(),
                alipayProperties.getAlipayPublicKey(),
                alipayProperties.getSignType()
        );
        return alipayClient;
    }
}
