package com.youyu.cardequity.payment.biz.component.strategy.sqlqueryengine;

import com.youyu.cardequity.payment.biz.dal.entity.SqlQueryEngine;

import java.util.Map;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2019年4月21日 10:00:00
 * @work sql查询引擎解析策略
 */
public abstract class SqlQueryEngineParseStrategy {

    /**
     * 解析策略
     *
     * @param sqlQueryEngine
     * @param paramMap
     * @return
     */
    public abstract String parse(SqlQueryEngine sqlQueryEngine, Map<String, Object> paramMap);
}
