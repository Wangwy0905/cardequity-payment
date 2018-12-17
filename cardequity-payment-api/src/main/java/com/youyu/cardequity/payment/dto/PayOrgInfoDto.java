package com.youyu.cardequity.payment.dto;

import com.youyu.cardequity.common.base.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付机构信息Dto
 */
@Data
@ApiModel
public class PayOrgInfoDto extends BasePageQuery<String> {

    @ApiModelProperty(value = "支付机构号")
    private String payOrgNo;

    @ApiModelProperty(value = "支付机构名称")
    private String payOrgName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @Override
    public String getId() {
        return payOrgNo;
    }

    @Override
    public void setId(String payOrgNo) {
        this.payOrgNo = payOrgNo;
    }
}
