package com.youyu.cardequity.payment.api;

import com.youyu.cardequity.payment.dto.AlipayPrepayment4PayLogDto;
import com.youyu.cardequity.payment.dto.PayLogDto;
import com.youyu.cardequity.payment.dto.alipay.AlipaySyncMessageDto;
import com.youyu.cardequity.payment.dto.alipay.AlipayTradeCloseDto;
import com.youyu.cardequity.payment.dto.alipay.AlipayTradeQueryDto;
import com.youyu.common.api.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月12日 下午10:00:00
 * @work 支付日志Api定义
 */
@FeignClient(name = "cardequity-payment")
@RequestMapping(path = "/payLog")
public interface PayLogApi {

    /**
     * 支付宝预支付接口
     *
     * @param payLogDto
     * @return
     */
    @ApiOperation(value = "支付宝预支付")
    @PostMapping(value = "/alipay")
    Result<AlipayPrepayment4PayLogDto> alipay(@RequestBody PayLogDto payLogDto);

    /**
     * 支付宝同步通知结果
     *
     * @param alipaySyncMessageDto
     * @return
     */
    @ApiOperation(value = "支付宝同步通知结果")
    @PostMapping(value = "/alipaySyncMessage")
    Result alipaySyncMessage(@RequestBody AlipaySyncMessageDto alipaySyncMessageDto);

    /**
     * 支付宝异步通知结果
     *
     * @param httpServletRequest
     * @return
     */
    @ApiOperation(value = "支付宝异步通知结果")
    @PostMapping(value = "/alipayAsyncMessage")
    String alipayAsyncMessage(HttpServletRequest httpServletRequest);

    /**
     * 支付宝统一收单交易关闭接口
     *
     * @param alipayTradeCloseDto
     * @return
     */
    @ApiOperation(value = "支付宝统一收单交易关闭接口")
    @PostMapping(value = "/alipayTradeClose")
    Result alipayTradeClose(@RequestBody AlipayTradeCloseDto alipayTradeCloseDto);

    /**
     * 支付宝统一收单线下交易查询接口
     *
     * @param alipayTradeQueryDto
     * @return
     */
    @ApiOperation(value = "支付宝统一收单线下交易查询接口")
    @PostMapping(value = "/alipayTradeQuery")
    Result alipayTradeQuery(@RequestBody AlipayTradeQueryDto alipayTradeQueryDto);

}
