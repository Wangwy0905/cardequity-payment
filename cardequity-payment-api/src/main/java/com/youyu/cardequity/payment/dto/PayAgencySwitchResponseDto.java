package com.youyu.cardequity.payment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付机构开关响应模型Dto
 */
@Data
@ApiModel("支付机构开关响应模型")
public class PayAgencySwitchResponseDto implements Serializable {

    private static final long serialVersionUID = -4598785854258082424L;

    @ApiModelProperty("支付开关:true:生产支付;false:测试支付")
    private Boolean paySwitch;
}
