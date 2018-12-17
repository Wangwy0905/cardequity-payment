package com.youyu.cardequity.payment.dto;

import com.youyu.cardequity.common.base.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付渠道信息Dto
 */
@Data
@ApiModel
public class PayChannelInfoDto extends BasePageQuery<String> {

    @ApiModelProperty(value = "渠道号")
    private String channelNo;

    @ApiModelProperty(value = "渠道名称")
    private String channelName;

    @ApiModelProperty(value = "支付机构号")
    private String payOrgNo;

    @ApiModelProperty(value = "支付接口类型")
    private String payInterfaceType;

    @ApiModelProperty(value = "支付模式")
    private String payMode;

    @ApiModelProperty(value = "校验方式")
    private String checkLevel;

    @ApiModelProperty(value = "连续调用失败笔数")
    private Integer continuityFailNum;

    @ApiModelProperty(value = "支持的证件类型")
    private String certificateTypes;

    @ApiModelProperty(value = "状态:0-正常、1-维护中、2-自动检测异常、3-暂停")
    private String state;

    @ApiModelProperty(value = "签约顺序:越小优先级越高:优先签约")
    private Integer signOrder;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "支付工厂:1:支付宝")
    private String payLogFactoryNumber;

    @ApiModelProperty(value = "支付策略:1:支付宝")
    private String payStrategyNumber;

    @Override
    public String getId() {
        return channelNo;
    }

    @Override
    public void setId(String channelNo) {
        this.channelNo = channelNo;
    }
}
