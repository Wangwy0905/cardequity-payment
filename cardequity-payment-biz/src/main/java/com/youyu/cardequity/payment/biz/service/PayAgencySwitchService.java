package com.youyu.cardequity.payment.biz.service;

import com.youyu.cardequity.payment.dto.PayAgencySwitchDto;
import com.youyu.cardequity.payment.dto.PayAgencySwitchResponseDto;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 支付机构开关 Service
 */
public interface PayAgencySwitchService {

    /**
     * 根据请求参数获取支付机构开关
     *
     * @param payAgencySwitchDto
     * @return
     */
    PayAgencySwitchResponseDto getPayAgencySwitch(PayAgencySwitchDto payAgencySwitchDto);
}
