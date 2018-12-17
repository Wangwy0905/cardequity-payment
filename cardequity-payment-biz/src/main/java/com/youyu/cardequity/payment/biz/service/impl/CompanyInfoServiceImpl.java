package com.youyu.cardequity.payment.biz.service.impl;

import com.youyu.cardequity.common.base.base.BaseMapper;
import com.youyu.cardequity.common.base.base.BaseServiceImpl;
import com.youyu.cardequity.payment.biz.dal.dao.CompanyInfoMapper;
import com.youyu.cardequity.payment.biz.dal.entity.CompanyInfo;
import com.youyu.cardequity.payment.biz.service.CompanyInfoService;
import com.youyu.cardequity.payment.dto.CompanyInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work CompanyInfo商户信息Service impl
 */
@Service
public class CompanyInfoServiceImpl extends BaseServiceImpl<CompanyInfoDto, CompanyInfo> implements CompanyInfoService {

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return companyInfoMapper;
    }
}
