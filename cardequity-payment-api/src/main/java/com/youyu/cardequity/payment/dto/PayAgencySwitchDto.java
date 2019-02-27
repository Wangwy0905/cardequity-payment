package com.youyu.cardequity.payment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付机构开关请求模型Dto
 */
@Data
@ApiModel("支付机构开关请求模型")
public class PayAgencySwitchDto implements Serializable {

    private static final long serialVersionUID = -4575054555642419746L;
    /**
     * 支付机构编码
     */
    @ApiModelProperty(value = "支付机构编码", example = "1:支付宝支付机构;")
    private String payAgencyCode;

}
