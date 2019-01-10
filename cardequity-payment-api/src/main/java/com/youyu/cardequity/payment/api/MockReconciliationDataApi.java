package com.youyu.cardequity.payment.api;

import com.youyu.cardequity.payment.dto.MockReconciliationDataDto;
import com.youyu.common.api.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月12日 下午10:00:00
 * @work mock对账造数据方便测试测 Api定义 注:仅用给测试造数据使用
 */
@Api(tags = "mock对账造数据方便测试测Api定义")
@FeignClient(name = "cardequity-payment")
@RequestMapping(path = "/mockReconciliationData")
public interface MockReconciliationDataApi {

    /**
     * 创建mock交易数据
     *
     * @param mockReconciliationDataDto
     * @return
     */
    @ApiOperation(value = "创建mock交易数据:TB_PAY_LOG,TB_PAY_CHECK_FILE_DEATAIL,TB_TRADE_ORDER")
    @PostMapping("/mockTradeData")
    Result mockTradeData(@RequestBody MockReconciliationDataDto mockReconciliationDataDto);

    /**
     * 创建mock退款数据
     *
     * @param mockReconciliationDataDto
     * @return
     */
    @ApiOperation(value = "创建mock退款数据:TB_PAY_LOG,TB_PAY_CHECK_FILE_DEATAIL,TB_TRADE_ORDER,TB_PAY_TRADE_REFUND")
    @PostMapping("/mockRefundData")
    Result mockRefundData(@RequestBody MockReconciliationDataDto mockReconciliationDataDto);
}