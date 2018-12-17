package com.youyu.cardequity.payment.biz.controller;

import com.youyu.cardequity.common.base.base.BaseController;
import com.youyu.cardequity.common.base.base.BaseService;
import com.youyu.cardequity.payment.api.ChannelFareInfoApi;
import com.youyu.cardequity.payment.biz.service.ChannelFareInfoService;
import com.youyu.cardequity.payment.dto.ChannelFareInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 渠道费率Controller
 */
@RestController
@RequestMapping(value = "/channelFareInfo")
public class ChannelFareInfoController extends BaseController<ChannelFareInfoDto> implements ChannelFareInfoApi {

    @Autowired
    private ChannelFareInfoService channelFareInfoService;

    @Override
    public BaseService getBaseService() {
        return channelFareInfoService;
    }
}
