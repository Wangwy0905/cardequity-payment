package com.youyu.cardequity.payment.api;

import com.youyu.cardequity.common.base.api.BaseQueryApi;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2019年5月22日 10:00:00
 * @work 支付查询Api
 */
@Api(tags = "支付查询Api")
@FeignClient(name = "cardequity-payment")
@RequestMapping(path = "/paymentQuery")
public interface PaymentQueryApi extends BaseQueryApi {

}
