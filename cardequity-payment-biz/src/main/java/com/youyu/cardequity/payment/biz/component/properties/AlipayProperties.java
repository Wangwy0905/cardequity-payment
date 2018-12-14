package com.youyu.cardequity.payment.biz.component.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work Alipay属性装载Bean
 */
@Setter
@Getter
@ConfigurationProperties(value = "alipay")
@Component
public class AlipayProperties implements Serializable {

    private static final long serialVersionUID = -1505571997009581142L;

    private String url;

    private String appId;

    private String appPrivateKey;

    private String format;

    private String charset;

    private String alipayPublicKey;

    private String signType;

    private String sellerId;

    private String notifyUrl;

    private Integer asyncNotifyThresholdStart = 15;

    private Integer asyncNotifyThresholdEnd = 10;

}
