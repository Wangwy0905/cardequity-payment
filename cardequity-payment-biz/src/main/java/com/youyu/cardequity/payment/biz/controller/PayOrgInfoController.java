package com.youyu.cardequity.payment.biz.controller;

import com.youyu.cardequity.common.base.base.BaseController;
import com.youyu.cardequity.common.base.base.BaseService;
import com.youyu.cardequity.payment.api.PayOrgInfoApi;
import com.youyu.cardequity.payment.biz.service.PayOrgInfoService;
import com.youyu.cardequity.payment.dto.PayOrgInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 支付机构Controller
 */
@RestController
@RequestMapping(value = "/payOrgInfo")
public class PayOrgInfoController extends BaseController<PayOrgInfoDto> implements PayOrgInfoApi {

    @Autowired
    private PayOrgInfoService payOrgInfoService;

    @Override
    public BaseService getBaseService() {
        return payOrgInfoService;
    }
}
