package com.youyu.cardequity.payment.biz.controller;

import com.youyu.cardequity.payment.api.PayLogApi;
import com.youyu.cardequity.payment.biz.service.PayLogService;
import com.youyu.cardequity.payment.dto.PayLogDto;
import com.youyu.cardequity.payment.dto.PayLogResponseDto;
import com.youyu.cardequity.payment.dto.TradeCloseDto;
import com.youyu.cardequity.payment.dto.TradeCloseResponseDto;
import com.youyu.cardequity.payment.dto.alipay.AlipaySyncMessageDto;
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

    @Override
    @PostMapping(value = "/pay")
    public Result<PayLogResponseDto> pay(@RequestBody PayLogDto payLogDto) {
        return ok(payLogService.pay(payLogDto));
    }

    @Override
    @PostMapping(value = "/alipaySyncMessage")
    public Result alipaySyncMessage(@RequestBody AlipaySyncMessageDto alipaySyncMessageDto) {
        payLogService.alipaySyncMessage(alipaySyncMessageDto);
        return ok();
    }

    @Override
    @PostMapping(value = "/alipayAsyncMessage")
    public String alipayAsyncMessage(HttpServletRequest httpServletRequest) {
        Map<String, String> params2Map = params2Map(httpServletRequest);
        return payLogService.alipayAsyncMessage(params2Map);
    }

    @Override
    @PostMapping(value = "/tradeClose")
    public Result<TradeCloseResponseDto> tradeClose(@RequestBody TradeCloseDto tradeCloseDto) {
        return ok(payLogService.tradeClose(tradeCloseDto));
    }

    @Override
    @PostMapping(value = "/timeTradeQuery")
    public Result timeTradeQuery() {
        payLogService.timeTradeQuery();
        return ok();
    }
}
