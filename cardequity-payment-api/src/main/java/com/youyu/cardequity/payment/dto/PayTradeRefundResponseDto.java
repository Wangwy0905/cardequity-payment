package com.youyu.cardequity.payment.dto;

import com.youyu.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 退款响应DTO
 */
@Data
@ApiModel
public class PayTradeRefundResponseDto extends BaseDto<String> {

    private static final long serialVersionUID = 1019448792108327168L;

    /**
     * 未退款
     */
    public static final String STATUS_NON_REFUND = "0";
    /**
     * 退款中
     */
    public static final String STATUS_REFUNDING = "1";

    /**
     * 退款成功
     */
    public static final String STATUS_SUCC = "2";

    /**
     * 退款失败
     */
    public static final String STATUS_FAIL = "3";

    @ApiModelProperty(value = "订单编号")
    private String appSheetSerialNo;

    @ApiModelProperty(value = "退款编号")
    private String refundNo;

    @ApiModelProperty(value = "退款状态")
    private String refundStatus;
}
