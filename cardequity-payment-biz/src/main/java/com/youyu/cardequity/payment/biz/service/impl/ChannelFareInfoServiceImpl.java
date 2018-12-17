package com.youyu.cardequity.payment.biz.service.impl;

import com.youyu.cardequity.common.base.base.BaseMapper;
import com.youyu.cardequity.common.base.base.BaseServiceImpl;
import com.youyu.cardequity.payment.biz.dal.dao.ChannelFareInfoMapper;
import com.youyu.cardequity.payment.biz.dal.entity.ChannelFareInfo;
import com.youyu.cardequity.payment.biz.service.ChannelFareInfoService;
import com.youyu.cardequity.payment.dto.ChannelFareInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 渠道费率Service impl
 */
@Service
public class ChannelFareInfoServiceImpl extends BaseServiceImpl<ChannelFareInfoDto, ChannelFareInfo> implements ChannelFareInfoService {

    @Autowired
    private ChannelFareInfoMapper channelFareInfoMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return channelFareInfoMapper;
    }
}
