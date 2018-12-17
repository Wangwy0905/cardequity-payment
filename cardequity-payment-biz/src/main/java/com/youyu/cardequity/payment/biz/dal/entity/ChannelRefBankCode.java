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
 * @work 渠道银行信息
 */
@Getter
@Setter
@Table(name = "TB_CHANNEL_REF_BANK_CODE")
public class ChannelRefBankCode extends BaseEntity<String> {

    /**
     * 主键:
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
     * 银行代码:
     */
    @Column(name = "BANK_CODE")
    private String bankCode;

    /**
     * 支付接口类型:字典100001
     */
    @Column(name = "PAY_INTERFACE_TYPE")
    private String payInterfaceType;

    /**
     * 支付模式:字典100002
     */
    @Column(name = "PAY_MODE")
    private String payMode;

    /**
     * 单日累计限额:
     */
    @Column(name = "PER_DAY_UP")
    private BigDecimal perDayUp;

    /**
     * 单人当日限额:为0标识不控制
     */
    @Column(name = "PER_DAY_AND_PERSON_UP")
    private BigDecimal perDayAndPersonUp;

    /**
     * 单笔限额:
     */
    @Column(name = "MAX_AMOUNT")
    private BigDecimal maxAmount;

    /**
     * 连续失败笔数:自动统计的，当该值大于系统配置参数时将Tb_PayChannelInfo.State置为2
     */
    @Column(name = "CONTINUITY_FAIL_NUM")
    private Integer continuityFailNum;

    /**
     * 短信发送模式:0-银行或第三方发送 1-平台发送
     */
    @Column(name = "MSG_SEND_FLAG")
    private String msgSendFlag;

    /**
     * 状态:字典100004
     */
    @Column(name = "STATE")
    private String state;

    /**
     * 备注:
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 日期统计类型:0-15点分界 1-0点分界 2-工作日
     */
    @Column(name = "DATE_FLAG")
    private String dateFlag;

    /**
     * 路由权重值:0-100，100为最优先的值，运营长期观察后设置
     */
    @Column(name = "WEIGHT_VALUE")
    private Integer weightValue;

    /**
     * 渠道的银行代码:
     */
    @Column(name = "CHANNEL_BANK_CODE")
    private String channelBankCode;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
