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

    /*private String url = "https://openapi.alipay.com/gateway.do";

    private String appId = "2018120562456421";

    private String appPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCHyQO9Dl44OHydhd0tGCJmDeXCPO1+NSZf8Sxa6Docc6EF+OM74gQWl2PosJsbgqU4fDjsyyFcNT7ckOKOnR5e7Pd6Bgo5MAVMYDKMtmkwWak4E50OVY+NFE/6kgsYWm16dMquZCJMcctdvh7ArOLt+NyqNSFEE14Acv0vmndMThXxa8yjOG3WN1o5WWG6P0s/b02Q4DXJlUGDMSBC+8iPTGCW67Z8dgAVHXClRF5LQE0jeWA1HjKYammVo/ibyNR8aMyg1dUZuQ+7KQv6pxOgqndA7+lUMFXILKHC4IxVl1liyZXwdhfYaAeR9OVZNA9cSzFOa2EhmPRSqcY8JV1BAgMBAAECggEAOpWsG0G7vR4CBiTfjLlA/i841llbTny4FeUEciSIDgaFjwIXaJrXkLe4z4iDI5d2maxfEyuBNTtz585LMaTwmZw8GlXIy2vAnXdT2ApL0CSfMiakOK8JxKDi8QVZ7CP34EHLwPpLIhhm2gdRVWhCcNJSws0xkSGaCS+TtZo7ppvVP4prC15uuSti59i8HPCpgcS6UrC8EMH2dfVfX2YshEKWHN1gKprSjO9aaLp7iBWFOSg9GxCeIe5TIOYIcuUR4cOCMV70MnzKI/0IGcgVlrE5DC7PQ4RMZBpxQH5LkBIrp1g+DcaXlItlrieUJym1/CprDK6I/alhECTrUSZTAQKBgQDpXC9JkQ0OYYbEQ6feJTvLa0xs3lU0yJ5fwnEX9b7yHK88UzB3NgGm4ot7G5tY3JT6jS3JBkORoWbpJd5/KqowUyeQLloirnVj+MOYlSimzCd7+f3Ln2A2e/xSH3jdV+V4fO7I1/XDeJfRKfor3IgaJQaSpgoUNKEKuSoPJG7XeQKBgQCU9WzMdiToYuOvBu9L1TcCpkS8xyhUro7hiU6rS8Zqy9q4kVf5WGPJt9J2iGu+dwZ2akgOqSu9Ag4RFYz3tpn7DNmgf/HQdsWHwVE+Bu+sZcdSEgmpEx6QSHgcsba9PPIgHVYQAHL0RNfC3jVMdOeqRWybjSsuUpnWwRl8lmmaCQKBgCpl0EWab/fT6ktQS3iF5UZBGWKSRoLPjE6zoh6LTITqhz/7kUunaUdr05GrY18SvJKFAfOLCs2zuGHVuoX6N2+OPBrrAP9aGQPv14GqX/dmEippMh5Dt5ROlHpLM4drh8qLUEr3SbSY/WRILya265dscLRGLICdS0EKgUS9BtJpAoGAASVCCPB/qCnahmYPQQ4KtkKBFNKTRNNy1dkwebDDfd7Dd9fmTUyYt9xqCe9SpMxViGOY2kQCyhlR8l+wDdehp4SZx9o0Cd5f9oNOFKQJA9JkjgUFff9fpMxp4A6d0Sa+1fBj0Quv1qEQ57a9wjDD91NBWpDjXB/WfwhNIeAmP8ECgYEAt6LKlkFIKiNoOwiFU28/VZZq6mKjAF6I1mCQ2NrUZrS8ktDpwrJkmHEj7/cEMSB0Uw2onATsqIv++v0ix8nXxNJ69yMGEsN95Itu47g7gu3p0xHswhxN8jAu8DVPj5E0aR8PNTrF6Z+w9/phif5pL7kJwDCC9dYVUpVlDUS5ouY=";

    private String format = "json";

    private String charset = "utf-8";

    private String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzNt4c57XQInYc2aWSIxklh/v5+bMTOufUsfCj+os7jwknTELswNhtk2bfSKW7vY2LWHIfXBiSprgzeMMUz9ZVKb3qnBWOal0Y/YI8L2ck1uF6Pox+35DNN+/qfE5wemWc5viW9vWtNLQx/RzXFomAbRq7zOSuCg8zLFTadyAVOUE1zb2o+21rf7w6sasG5td11K/sw7h1G66kEVm/T/aRi37wCRNxbXgxy+51aRnJSWCQHqbBdAozQAvfY2p4GDfdCkpXvbRGBkAGi1pxYJDSA30ROOp9kAslxRiqbMVCPtgyq/rh245V8Kks58iIil8ZoyEA5JuP4uHD9DNQbJKYwIDAQAB";

    private String signType = "RSA2";

    private String sellerId = "2088331717958134";

    private String notifyUrl = "http://pqq.vipgz1.idcfengye.com/payment/receive";

    private Integer asyncNotifyThresholdStart = 15;

    private Integer asyncNotifyThresholdEnd = 10;

    private String billFilePath = "/Users/panqingqing/Downloads/alipay/bill/";*/

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

    private String billFilePath;

}
