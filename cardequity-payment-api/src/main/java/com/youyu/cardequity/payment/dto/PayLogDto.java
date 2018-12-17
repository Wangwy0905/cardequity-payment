package com.youyu.cardequity.payment.dto;

import com.youyu.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work PayLog支付日志Dto
 */
@Data
@ApiModel
public class PayLogDto extends BaseDto<String> {

    private static final long serialVersionUID = 1340491747252445259L;

    /**
     * 支付宝支付渠道
     */
    public static final String PAY_CHANNEL_NO_ALIPAY = "000001";

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "自然日")
    private LocalDate initDate;

    @ApiModelProperty(value = "订单编号")
    private String appSheetSerialNo;

    @ApiModelProperty(value = "交易账号")
    private String transAccountId;

    @ApiModelProperty(value = "客户号")
    private String clientId;

    @ApiModelProperty(value = "客户姓名")
    private String clientName;

    @ApiModelProperty(value = "发生金额")
    private BigDecimal occurBalance;

    @ApiModelProperty(value = "支付渠道号")
    private String payChannelNo;

    @ApiModelProperty(value = "证件类别")
    private String certificateType;

    @ApiModelProperty(value = "证件号码")
    private String certificateNo;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "商品的标题、交易标题、订单标题、订单关键字等")
    private String subject;

    @ApiModelProperty(value = "该笔订单允许的最晚付款时间、逾期将关闭交易，默认15d")
    private String timeoutExpress;

    @ApiModelProperty(value = "商品主类型：0—虚拟类商品，1—实物类商品")
    private String goodsType;


}
