package com.youyu.cardequity.payment.dto;

import com.youyu.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月8日 下午10:00:00
 * @work 交易关闭响应
 */
@Data
@ApiModel
public class TradeCloseResponseDto extends BaseDto<String> {

    private static final long serialVersionUID = 1018396513813297128L;

    @ApiModelProperty(value = "订单编号")
    private String appSheetSerialNo;

    @ApiModelProperty(value = "交易关闭标志:true 关闭")
    private Boolean tradeCloseFlag;
}
