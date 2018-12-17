package com.youyu.cardequity.payment.biz.dal.entity;

import com.youyu.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付机构信息
 */
@Getter
@Setter
@Table(name = "TB_PAY_ORG_INFO")
public class PayOrgInfo extends BaseEntity<String> {

    /**
     * 支付机构号:手动定义，不重复即可
     */
    @Id
    @Column(name = "PAY_ORG_NO")
    private String payOrgNo;

    /**
     * 支付机构名称:如"银联支付,连连支付"
     */
    @Column(name = "PAY_ORG_NAME")
    private String payOrgName;

    /**
     * 备注:
     */
    @Column(name = "REMARK")
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
