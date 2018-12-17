package com.youyu.cardequity.payment.biz.controller;

import com.youyu.cardequity.common.base.base.BaseController;
import com.youyu.cardequity.common.base.base.BaseService;
import com.youyu.cardequity.payment.api.CompanyInfoApi;
import com.youyu.cardequity.payment.biz.service.CompanyInfoService;
import com.youyu.cardequity.payment.dto.CompanyInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 商户信息Controller
 */
@RestController
@RequestMapping(value = "/companyInfo")
public class CompanyInfoController extends BaseController<CompanyInfoDto> implements CompanyInfoApi {

    @Autowired
    private CompanyInfoService companyInfoService;

    @Override
    public BaseService getBaseService() {
        return companyInfoService;
    }
}
