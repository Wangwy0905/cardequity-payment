package com.youyu.cardequity.payment.dto;

import com.youyu.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月8日 下午10:00:00
 * @work 交易关闭参数
 */
@Data
@ApiModel
public class TradeCloseDto extends BaseDto<String> {

    private static final long serialVersionUID = 3000039385227954773L;

    @ApiModelProperty(value = "订单编号")
    private String appSheetSerialNo;

    @ApiModelProperty(value = "卖家端自定义的的操作员ID,可选")
    private String operatorId;

}
