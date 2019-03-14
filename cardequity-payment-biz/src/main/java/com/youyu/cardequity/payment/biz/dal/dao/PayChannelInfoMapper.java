package com.youyu.cardequity.payment.biz.dal.dao;

import com.youyu.cardequity.common.base.annotation.SpringBean;
import com.youyu.cardequity.common.base.base.BaseMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayChannelInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 支付渠道Mapper
 */
@SpringBean
public interface PayChannelInfoMapper extends BaseMapper<PayChannelInfo, String> {

    /**
     * 根据支付渠道号查询
     *
     * @param channelNo
     * @return
     */
    PayChannelInfo getById(@Param("channelNo") String channelNo);

    /**
     * 根据支付日志编号查询支付渠道
     *
     * @param payLogId
     * @return
     */
    PayChannelInfo getByPayLogId(@Param("payLogId") String payLogId);
}
