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
 * @work 支付宝同步通知结果
 */
@Data
@ApiModel
public class AlipaySyncMessageDto implements Serializable {

    private static final long serialVersionUID = -6473340282982301041L;

    @ApiModelProperty(value = "描述")
    private String memo;

    @ApiModelProperty(value = "描述:9000:订单支付成功")
    private String resultStatus;

    @JsonProperty(value = "result")
    @ApiModelProperty(value = "支付宝同步通知结果中的result部分")
    private AlipaySyncMessageResultDto alipaySyncMessageResultDto;

}
