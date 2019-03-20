package com.youyu.cardequity.payment.dto;

import com.youyu.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 对账信息Dto
 */
@Data
@ApiModel
public class PayCheckDeatailDto extends BaseDto<String> {

    private static final long serialVersionUID = 1347048629460015502L;

    @ApiModelProperty(value = "对账日期:默认当前日期前一天")
    private String billDate;

}
