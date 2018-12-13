package com.youyu.cardequity.payment.dto.alipay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月8日 下午10:00:00
 * @work 支付宝交易查询参数
 */
@Data
@ApiModel
public class AlipayTradeQueryDto implements Serializable {

    private static final long serialVersionUID = 1778704493766074533L;

    @ApiModelProperty(value = "订单编号")
    private String appSheetSerialNo;

    @ApiModelProperty(value = "补偿标志:true:补偿;其他:不补偿")
    private Boolean compensateFlag;
}
