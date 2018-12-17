package com.youyu.cardequity.payment.api;

import com.youyu.cardequity.common.base.base.BaseApi;
import com.youyu.cardequity.payment.dto.CompanyInfoDto;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月12日 下午10:00:00
 * @work 渠道商户Api定义
 */
@Api(tags = "渠道商户管理Api")
@FeignClient(name = "cardequity-payment")
@RequestMapping(path = "/companyInfo")
public interface CompanyInfoApi extends BaseApi<CompanyInfoDto> {
}
