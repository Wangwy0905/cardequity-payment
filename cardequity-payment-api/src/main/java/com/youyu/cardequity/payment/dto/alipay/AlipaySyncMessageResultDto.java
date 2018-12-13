package com.youyu.cardequity.payment.dto.alipay;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月8日 下午10:00:00
 * @work 支付宝同步通知结果中的result部分
 */
@Data
@ApiModel
public class AlipaySyncMessageResultDto implements Serializable {

    private static final long serialVersionUID = -3191904738992335112L;

    @ApiModelProperty(value = "签名信息")
    private String sign;

    @JsonProperty(value = "sign_type")
    @ApiModelProperty(value = "签名类型")
    private String signType;

    @JsonProperty(value = "alipay_trade_app_pay_response")
    @ApiModelProperty(value = "alipay_trade_app_pay_response部分")
    private AlipaySyncMessageResponseDto alipayTradeAppPayResponse;

}
