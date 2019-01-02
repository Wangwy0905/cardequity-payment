package com.youyu.cardequity.payment.biz.controller;

import com.youyu.cardequity.payment.api.PayCheckDeatailApi;
import com.youyu.cardequity.payment.biz.service.PayCheckDeatailService;
import com.youyu.cardequity.payment.dto.PayCheckDeatailDto;
import com.youyu.common.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.youyu.common.api.Result.ok;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 对账信息管理Controller
 */
@RestController
@RequestMapping(value = "/payCheckDeatail")
public class PayCheckDeatailController implements PayCheckDeatailApi {

    @Autowired
    private PayCheckDeatailService payCheckDeatailService;

    @Override
    public Result reconciliation(PayCheckDeatailDto payCheckDeatailDto) {
        payCheckDeatailService.reconciliation(payCheckDeatailDto, payCheckDeatailService);
        return ok();
    }
}
