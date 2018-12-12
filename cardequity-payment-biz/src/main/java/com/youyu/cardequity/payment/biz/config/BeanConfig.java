package com.youyu.cardequity.payment.biz.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${alipay.url}")
    private String url;

    @Value("${alipay.appId}")
    private String appId;

    @Value("${alipay.appPrivateKey}")
    private String appPrivateKey;

    @Value("${alipay.format:json}")
    private String format;

    @Value("${alipay.charset:utf-8}")
    private String charset;

    @Value("${alipay.alipayPublicKey}")
    private String alipayPublicKey;

    @Value("${alipay.signType:RSA2}")
    private String signType;

    /**
     * 支付宝支付客户端
     *
     * @return
     */
    @Bean
    public AlipayClient alipayClient() {
        AlipayClient alipayClient = new DefaultAlipayClient(url, appId, appPrivateKey, format, charset, alipayPublicKey, signType);
        return alipayClient;
    }
}
