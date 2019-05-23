package com.youyu.cardequity.payment.biz.help.query;

import com.youyu.cardequity.common.base.annotation.SqlQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2019年5月23日 10:00:00
 * @work 支付查询Dto
 */
@SqlQuery(name = "payQuery")
@ApiModel("支付查询Dto")
@Setter
@Getter
public class PayQueryDto implements Serializable {

    private static final long serialVersionUID = -8047935799560286282L;

    @ApiModelProperty("支付创建时间")
    private LocalDateTime payCreateTime;

    @ApiModelProperty("支付编号")
    private String payId;

    @ApiModelProperty("支付金额")
    private BigDecimal payAmount;

    @ApiModelProperty("业务类型")
    private String businCode;

    @ApiModelProperty("支付方式")
    private String payType;

    @ApiModelProperty("支付状态")
    private String payStatus;

    @ApiModelProperty("订单编号")
    private String orderId;

    @ApiModelProperty("用户id")
    private String clientId;

    @ApiModelProperty("支付时间")
    private LocalDateTime payTime;
}
