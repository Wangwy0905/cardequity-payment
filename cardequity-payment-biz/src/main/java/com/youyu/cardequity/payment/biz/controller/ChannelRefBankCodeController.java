package com.youyu.cardequity.payment.biz.controller;

import com.youyu.cardequity.common.base.base.BaseController;
import com.youyu.cardequity.common.base.base.BaseService;
import com.youyu.cardequity.payment.api.ChannelRefBankCodeApi;
import com.youyu.cardequity.payment.biz.service.ChannelRefBankCodeService;
import com.youyu.cardequity.payment.dto.ChannelRefBankCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 渠道银行信息Controller
 */
@RestController
@RequestMapping(value = "/channelRefBankCode")
public class ChannelRefBankCodeController extends BaseController<ChannelRefBankCodeDto> implements ChannelRefBankCodeApi {

    @Autowired
    private ChannelRefBankCodeService channelRefBankCodeService;

    @Override
    public BaseService getBaseService() {
        return channelRefBankCodeService;
    }
}
