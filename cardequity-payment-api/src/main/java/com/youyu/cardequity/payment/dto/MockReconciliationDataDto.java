package com.youyu.cardequity.payment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work mock对账造数据方便测试 Dto
 */
@Data
@ApiModel
public class MockReconciliationDataDto implements Serializable {

    private static final long serialVersionUID = 6294882331927545671L;

    @ApiModelProperty(value = "数据数量", example = "10")
    private Integer quantity;

    @ApiModelProperty(value = "是否是交易", example = "true:交易;false:退款,默认交易")
    private Boolean isTrade = true;
}
