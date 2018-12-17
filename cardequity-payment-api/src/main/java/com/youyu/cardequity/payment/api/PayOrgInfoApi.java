package com.youyu.cardequity.payment.api;

import com.youyu.cardequity.common.base.base.BaseApi;
import com.youyu.cardequity.payment.dto.PayOrgInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月12日 下午10:00:00
 * @work 支付机构Api定义
 */
@FeignClient(name = "cardequity-payment")
@RequestMapping(path = "/payOrgInfo")
public interface PayOrgInfoApi extends BaseApi<PayOrgInfoDto> {
}
