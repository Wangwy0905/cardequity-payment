package com.youyu.cardequity.payment.biz.service;

import com.youyu.cardequity.payment.dto.SqlQueryEngineResponseDto;

import java.util.Map;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2019年4月22日 10:00:00
 * @work sql查询引擎 service
 */
public interface SqlQueryEngineService {

    /**
     * 获取执行sql
     *
     * @param paramMap
     * @return
     */
    SqlQueryEngineResponseDto getSqlQueryEngine(Map<String, String> paramMap);
}
