package com.youyu.cardequity.payment.dto;

import com.youyu.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付宝PayLog预支付日志Dto
 */
@Data
@ApiModel
public class AlipayPrepayment4PayLogDto extends BaseDto<String> {

    private static final long serialVersionUID = 39561118107733664L;

    @ApiModelProperty(value = "支付机构返回结果")
    private String respInfo;
}
