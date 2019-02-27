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
 * @work 支付渠道开关配置
 */
@Getter
@Setter
@Table(name = "TB_PAY_AGENCY_SWITCH")
public class PayAgencySwitch extends BaseEntity<String> {

    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 支付机构名称
     */
    @Column(name = "PAY_AGENCY_NAME")
    private String payAgencyName;

    /**
     * 支付机构代码
     */
    @Column(name = "PAY_AGENCY_CODE")
    private String payAgencyCode;

    /**
     * 支付开关:true:生产支付;false:测试支付
     */
    @Column(name = "PAY_SWITCH")
    private Boolean paySwitch;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
