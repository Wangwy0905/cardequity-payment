package com.youyu.cardequity.payment.biz.dal.entity;

import com.youyu.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 渠道费率
 */
@Getter
@Setter
@Table(name = "TB_CHANNEL_FARE_INFO")
public class ChannelFareInfo extends BaseEntity<String> {

    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 渠道号:
     */
    @Column(name = "CHANNEL_NO")
    private String channelNo;

    /**
     * 公司Id:
     */
    @Column(name = "COMPANY_ID")
    private String companyId;

    /**
     * 金额段起始（不含）:
     */
    @Column(name = "AMOUNT_LEVEL_START")
    private BigDecimal amountLevelStart;

    /**
     * 金额段结束（含）:
     */
    @Column(name = "AMOUNT_LEVEL_END")
    private BigDecimal amountLevelEnd;

    /**
     * 最高费用:
     */
    @Column(name = "MAX_FARE")
    private BigDecimal maxFare;

    /**
     * 最低费用:
     */
    @Column(name = "MIN_FARE")
    private BigDecimal minFare;

    /**
     * 具体费率(%):可为0,使用时需要除以100
     */
    @Column(name = "FARE_RATIO")
    private BigDecimal fareRatio;

    /**
     * 固定费用:当该值不为0，FareRatio为0则为按笔收费
     */
    @Column(name = "FIXED_FARE")
    private BigDecimal fixedFare;

    /**
     * 支持银行:多个银行用“,”号分割，*匹配所有银行
     */
    @Column(name = "RELATION_BANKS")
    private String relationBanks;

    /**
     * 备注:
     */
    @Column(name = "REMARK")
    private String remark;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
