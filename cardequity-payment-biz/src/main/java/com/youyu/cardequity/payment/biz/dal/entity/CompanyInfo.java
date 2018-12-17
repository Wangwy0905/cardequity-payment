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
 * @work 商户信息
 */
@Getter
@Setter
@Table(name = "TB_COMPANY_INFO")
public class CompanyInfo extends BaseEntity<String> {

    /**
     * 公司Id:手动定义，不重复即可
     */
    @Id
    @Column(name = "COMPANY_ID")
    private String companyId;

    /**
     * 公司名称:如"有鱼基金销售"
     */
    @Column(name = "COMPANY_NAME")
    private String companyName;

    /**
     * 备注:
     */
    @Column(name = "REMARK")
    private String remark;

    @Override
    public String getId() {
        return companyId;
    }

    @Override
    public void setId(String companyId) {
        this.companyId = companyId;
    }
}
