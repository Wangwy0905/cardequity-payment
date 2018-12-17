package com.youyu.cardequity.payment.biz.service.impl;

import com.youyu.cardequity.common.base.base.BaseMapper;
import com.youyu.cardequity.common.base.base.BaseServiceImpl;
import com.youyu.cardequity.payment.biz.dal.dao.PayChannelInfoMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayChannelInfo;
import com.youyu.cardequity.payment.biz.service.PayChannelInfoService;
import com.youyu.cardequity.payment.dto.PayChannelInfoDto;
import com.youyu.common.dto.BaseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付渠道Service impl
 */
@Service
public class PayChannelInfoServiceImpl extends BaseServiceImpl<PayChannelInfoDto, PayChannelInfo> implements PayChannelInfoService {

    @Autowired
    private PayChannelInfoMapper payChannelInfoMapper;

    @Override
    public void add(BaseDto dto) {
        PayChannelInfoDto payChannelInfoDto = (PayChannelInfoDto) dto;
        PayChannelInfo payChannelInfo = new PayChannelInfo(payChannelInfoDto);
        payChannelInfoMapper.insertSelective(payChannelInfo);
    }

    @Override
    public void update(BaseDto dto) {
        PayChannelInfoDto payChannelInfoDto = (PayChannelInfoDto) dto;
        PayChannelInfo payChannelInfo = new PayChannelInfo(payChannelInfoDto);
        payChannelInfo.setId(payChannelInfoDto.getId());
        payChannelInfo.setChannelNo(payChannelInfoDto.getChannelNo());
        payChannelInfoMapper.updateByPrimaryKeySelective(payChannelInfo);
    }

    @Override
    public BaseMapper getBaseMapper() {
        return payChannelInfoMapper;
    }
}
