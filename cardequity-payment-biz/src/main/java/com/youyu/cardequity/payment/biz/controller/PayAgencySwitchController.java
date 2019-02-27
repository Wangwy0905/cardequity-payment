package com.youyu.cardequity.payment.biz.controller;

import com.youyu.cardequity.payment.api.PayAgencySwitchApi;
import com.youyu.cardequity.payment.biz.service.PayAgencySwitchService;
import com.youyu.cardequity.payment.dto.PayAgencySwitchDto;
import com.youyu.cardequity.payment.dto.PayAgencySwitchResponseDto;
import com.youyu.common.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 支付机构开关Controller
 */
@RestController
@RequestMapping(value = "/payAgencySwitch")
public class PayAgencySwitchController implements PayAgencySwitchApi {

    @Autowired
    private PayAgencySwitchService payAgencySwitchService;

    @Override
    @PostMapping(value = "/getPayAgencySwitch")
    public Result<PayAgencySwitchResponseDto> getPayAgencySwitch(@RequestBody PayAgencySwitchDto payAgencySwitchDto) {
        PayAgencySwitchResponseDto payAgencySwitchResponseDto = payAgencySwitchService.getPayAgencySwitch(payAgencySwitchDto);
        return Result.ok(payAgencySwitchResponseDto);
    }
}
