package com.youyu.cardequity.payment.dto;

import com.youyu.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 对账文件明细信息Dto
 */
@Data
@ApiModel
public class PayCheckFileDeatailDto extends BaseDto<String> {

    @ApiModelProperty(value = "支付渠道号")
    private String channelNo;

    @ApiModelProperty(value = "对账日期:默认当前日期前一天")
    private String billDate;
}
