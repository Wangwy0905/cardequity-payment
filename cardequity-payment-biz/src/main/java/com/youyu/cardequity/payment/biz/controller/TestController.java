package com.youyu.cardequity.payment.biz.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.youyu.cardequity.common.base.util.WebUtil;
import com.youyu.cardequity.payment.biz.service.PayLogService;
import com.youyu.cardequity.payment.dto.PayLogDto;
import com.youyu.cardequity.payment.dto.PayLogResponseDto;
import com.youyu.cardequity.payment.dto.alipay.AlipaySyncMessageDto;
import com.youyu.common.api.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

import static com.youyu.cardequity.common.base.util.UuidUtil.uuid4NoRail;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@RequestMapping("/payment")
public class TestController {

    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxP5+1U3/0ovzQNb6tw2wDhoSwbohxKDI+/OdCsehDyMk7NWER07P37RLlAWRu0QhLUtseLna1+pfGLAo2BGHginHwZTxMHJnTq1sM4h0d4dAG7jbN1yTH/FMxYJ49WufK3112sEy4KUcmiChnlBsz0y7tNZnWYZJ/qsLM7gcbFBoYlvy/wM30MgIFtl8H7CQDHe4SJO1Eb7K0v2ilIhLRrc/GeKvHe7ZBmBmrrRaWZ3vHJUofFF4VKn961poeGmuYkuJqCz4AnhUlOKZhIjDdGncKsFU8rsWPIbx+QIbpq5h3Dssz2cG5ORcV8+drWGKYmgvyymKg8CWliLwVCObqQIDAQAB";

    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private PayLogService payLogService;

    @RequestMapping("/pay")
    public Result<String> pay() {
        PayLogDto payLogDto = new PayLogDto();
        payLogDto.setSubject("水果拼盘");
        payLogDto.setAppSheetSerialNo(uuid4NoRail());
//        payLogDto.setTimeoutExpress("90m");
        payLogDto.setOccurBalance(new BigDecimal("0.01"));
        payLogDto.setPayChannelNo("000001");
        PayLogResponseDto alipayPrepayment4PayLogDto = payLogService.pay(payLogDto);
        return Result.ok(alipayPrepayment4PayLogDto.getRespInfo());
    }

    @RequestMapping(value = "/rece", method = POST)
    public Result rece(@RequestBody AlipaySyncMessageDto alipaySyncMessageDto) {
        payLogService.alipaySyncMessage(alipaySyncMessageDto);
        return Result.ok();
    }


    @RequestMapping("/receive")
    public String receive(HttpServletRequest request) {
        Map<String, String> params2Map = WebUtil.params2Map(request);
        return payLogService.alipayAsyncMessage(params2Map);
    }

    @GetMapping("/get2")
    public Result<String> get2() {
        return Result.ok("aaa");
    }

    @GetMapping("/get3")
    public Result<String> get3() {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类
        request.setBizContent("{" +
                "   \"out_trade_no\":\"07311aa005cf407cab51b6eeaebed1e5\"" +
                "  }");//设置业务参数
        AlipayTradeQueryResponse response = null;//通过alipayClient调用API，获得对应的response类
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        System.out.print(response.getBody());
        return Result.ok("aaa");
    }


}
