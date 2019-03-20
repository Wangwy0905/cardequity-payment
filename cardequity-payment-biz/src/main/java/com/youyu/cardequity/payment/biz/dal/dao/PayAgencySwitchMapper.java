package com.youyu.cardequity.payment.biz.dal.dao;

import com.youyu.cardequity.payment.biz.dal.entity.PayAgencySwitch;
import com.youyu.cardequity.payment.dto.PayAgencySwitchDto;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 支付机构开关Mapper
 */
public interface PayAgencySwitchMapper {

    /**
     * 根据请求参数查询支付机构开关
     *
     * @param payAgencySwitchDto
     * @return
     */
    PayAgencySwitch getPayAgencySwitch(PayAgencySwitchDto payAgencySwitchDto);
}
