package com.youyu.cardequity.payment.biz.service.impl;

import com.youyu.cardequity.common.base.base.BaseMapper;
import com.youyu.cardequity.common.base.base.BaseServiceImpl;
import com.youyu.cardequity.payment.biz.dal.dao.ChannelRefBankCodeMapper;
import com.youyu.cardequity.payment.biz.dal.entity.ChannelRefBankCode;
import com.youyu.cardequity.payment.biz.service.ChannelRefBankCodeService;
import com.youyu.cardequity.payment.dto.ChannelRefBankCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 渠道银行信息Service impl
 */
@Service
public class ChannelRefBankCodeServiceImpl extends BaseServiceImpl<ChannelRefBankCodeDto, ChannelRefBankCode> implements ChannelRefBankCodeService {

    @Autowired
    private ChannelRefBankCodeMapper channelRefBankCodeMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return channelRefBankCodeMapper;
    }
}
