package com.youyu.cardequity.payment.biz.controller;

import com.youyu.cardequity.payment.api.PayCheckFileDeatailApi;
import com.youyu.cardequity.payment.biz.service.PayCheckFileDeatailService;
import com.youyu.cardequity.payment.dto.PayCheckFileDeatailDto;
import com.youyu.common.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.youyu.common.api.Result.ok;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 对账文件明细信息controller
 */
@RestController
@RequestMapping(value = "/payCheckFileDeatail")
public class PayCheckFileDeatailController implements PayCheckFileDeatailApi {

    @Autowired
    private PayCheckFileDeatailService payCheckFileDeatailService;

    @Override
    @PostMapping(value = "/downloadBill")
    public Result downloadBill(@RequestBody PayCheckFileDeatailDto payCheckFileDeatailDto) {
        payCheckFileDeatailService.downloadBill(payCheckFileDeatailDto);
        return ok();
    }
}
