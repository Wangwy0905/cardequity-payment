package com.youyu.cardequity.payment.biz.service.impl;

import com.youyu.cardequity.payment.biz.dal.dao.PayAgencySwitchMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayAgencySwitch;
import com.youyu.cardequity.payment.biz.service.PayAgencySwitchService;
import com.youyu.cardequity.payment.dto.PayAgencySwitchDto;
import com.youyu.cardequity.payment.dto.PayAgencySwitchResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.youyu.cardequity.common.base.converter.BeanPropertiesConverter.copyProperties;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 支付机构开关 Service 实现
 */
@Service
public class PayAgencySwitchServiceImpl implements PayAgencySwitchService {

    @Autowired
    private PayAgencySwitchMapper payAgencySwitchMapper;

    @Override
    public PayAgencySwitchResponseDto getPayAgencySwitch(PayAgencySwitchDto payAgencySwitchDto) {
        PayAgencySwitch payAgencySwitch = payAgencySwitchMapper.getPayAgencySwitch(payAgencySwitchDto);
        return copyProperties(payAgencySwitch, PayAgencySwitchResponseDto.class);
    }
}
