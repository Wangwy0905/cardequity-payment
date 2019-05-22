package com.youyu.cardequity.payment.biz.controller;

import com.youyu.cardequity.common.spring.base.BaseQueryController;
import com.youyu.cardequity.payment.api.PaymentQueryApi;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2019年5月22日 10:00:00
 * @work 支付查询Controller
 */
@Api(tags = "支付查询Api")
@RestController
@RequestMapping(path = "/paymentQuery")
public class PaymentQueryController extends BaseQueryController implements PaymentQueryApi {
}
