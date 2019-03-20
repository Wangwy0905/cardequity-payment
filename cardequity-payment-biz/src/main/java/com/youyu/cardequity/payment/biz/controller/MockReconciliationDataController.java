package com.youyu.cardequity.payment.biz.controller;

import com.youyu.cardequity.payment.api.MockReconciliationDataApi;
import com.youyu.cardequity.payment.biz.service.MockReconciliationDataService;
import com.youyu.cardequity.payment.dto.MockReconciliationDataDto;
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
 * @date 2018年12月10日 下午10:00:00
 * @work mock对账造数据方便测试测 Controller 注:仅用给测试造数据使用
 */
@RestController
@RequestMapping("/mockReconciliationData")
public class MockReconciliationDataController implements MockReconciliationDataApi {

    @Autowired
    private MockReconciliationDataService mockReconciliationDataService;

    @Override
    @PostMapping("/mockTradeData")
    public Result mockTradeData(@RequestBody MockReconciliationDataDto mockReconciliationDataDto) {
        mockReconciliationDataService.mockTradeData(mockReconciliationDataDto);
        return ok();
    }

    @Override
    @PostMapping("/mockRefundData")
    public Result mockRefundData(@RequestBody MockReconciliationDataDto mockReconciliationDataDto) {
        mockReconciliationDataService.mockRefundData(mockReconciliationDataDto);
        return ok();
    }
}
