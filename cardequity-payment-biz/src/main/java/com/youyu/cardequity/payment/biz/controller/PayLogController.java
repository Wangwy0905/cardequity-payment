package com.youyu.cardequity.payment.biz.controller;

import com.youyu.cardequity.payment.api.PayLogApi;
import com.youyu.cardequity.payment.biz.service.PayLogService;
import com.youyu.cardequity.payment.dto.AlipayPrepayment4PayLogDto;
import com.youyu.cardequity.payment.dto.PayLogDto;
import com.youyu.cardequity.payment.dto.alipay.AlipaySyncMessageDto;
import com.youyu.cardequity.payment.dto.alipay.AlipayTradeCloseDto;
import com.youyu.common.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.youyu.cardequity.common.base.util.WebUtil.params2Map;
import static com.youyu.common.api.Result.ok;


/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work PayLog支付日志controller
 */
@RestController
@RequestMapping(value = "/payLog")
public class PayLogController implements PayLogApi {

    @Autowired
    private PayLogService payLogService;

    /**
     * 支付宝预支付
     *
     * @param payLogDto
     * @return
     */
    @Override
    @PostMapping(value = "/alipay")
    public Result<AlipayPrepayment4PayLogDto> alipay(@RequestBody PayLogDto payLogDto) {
        AlipayPrepayment4PayLogDto alipayPrepayment4PayLogDto = payLogService.alipay(payLogDto, payLogService);
        return ok(alipayPrepayment4PayLogDto);
    }

    /**
     * 支付宝同步通知结果
     *
     * @param alipaySyncMessageDto
     * @return
     */
    @Override
    @PostMapping(value = "/alipaySyncMessage")
    public Result alipaySyncMessage(@RequestBody AlipaySyncMessageDto alipaySyncMessageDto) {
        payLogService.alipaySyncMessage(alipaySyncMessageDto);
        return ok();
    }

    /**
     * 支付宝异步通知结果
     *
     * @param httpServletRequest
     * @return
     */
    @Override
    @PostMapping(value = "/alipayAsyncMessage")
    public String alipayAsyncMessage(HttpServletRequest httpServletRequest) {
        Map<String, String> params2Map = params2Map(httpServletRequest);
        return payLogService.alipayAsyncMessage(params2Map);
    }

    /**
     * 支付宝统一收单交易关闭接口
     *
     * @param alipayTradeCloseDto
     * @return
     */
    @Override
    @PostMapping(value = "/alipayTradeClose")
    public Result alipayTradeClose(@RequestBody AlipayTradeCloseDto alipayTradeCloseDto) {
        return payLogService.alipayTradeClose(alipayTradeCloseDto);
    }

    /**
     * 定时任务调用:支付宝统一收单线下交易查询接口
     *
     * @return
     */
    @Override
    @PostMapping(value = "/timeAlipayTradeQuery")
    public Result timeAlipayTradeQuery() {
        payLogService.timeAlipayTradeQuery();
        return ok();
    }
}
