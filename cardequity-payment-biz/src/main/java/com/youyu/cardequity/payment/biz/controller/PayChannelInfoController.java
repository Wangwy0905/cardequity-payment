package com.youyu.cardequity.payment.biz.controller;

import com.youyu.cardequity.common.base.base.BaseController;
import com.youyu.cardequity.common.base.base.BaseService;
import com.youyu.cardequity.payment.api.PayChannelInfoApi;
import com.youyu.cardequity.payment.biz.service.PayChannelInfoService;
import com.youyu.cardequity.payment.dto.PayChannelInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 支付渠道Controller
 */
@RestController
@RequestMapping(value = "/payChannelInfo")
public class PayChannelInfoController extends BaseController<PayChannelInfoDto> implements PayChannelInfoApi {

    @Autowired
    private PayChannelInfoService payChannelInfoService;

    @Override
    public BaseService getBaseService() {
        return payChannelInfoService;
    }
}
