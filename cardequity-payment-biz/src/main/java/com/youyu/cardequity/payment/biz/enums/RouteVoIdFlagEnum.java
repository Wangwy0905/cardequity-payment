package com.youyu.cardequity.payment.biz.enums;

import lombok.Getter;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 路由抛弃标识
 */
@Getter
public enum RouteVoIdFlagEnum {

    NORMAL("0", "正常"),
    FAIL("1", "失败");

    private String code;

    private String msg;

    RouteVoIdFlagEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
