package com.youyu.cardequity.payment.biz.service.impl;

import com.youyu.cardequity.common.base.base.BaseMapper;
import com.youyu.cardequity.common.base.base.BaseServiceImpl;
import com.youyu.cardequity.payment.biz.dal.dao.PayOrgInfoMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayOrgInfo;
import com.youyu.cardequity.payment.biz.service.PayOrgInfoService;
import com.youyu.cardequity.payment.dto.PayOrgInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付机构Service impl
 */
@Service
public class PayOrgInfoServiceImpl extends BaseServiceImpl<PayOrgInfoDto, PayOrgInfo> implements PayOrgInfoService {

    @Autowired
    private PayOrgInfoMapper payOrgInfoMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return payOrgInfoMapper;
    }
}
