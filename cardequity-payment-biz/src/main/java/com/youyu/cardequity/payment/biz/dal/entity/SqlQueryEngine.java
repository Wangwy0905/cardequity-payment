package com.youyu.cardequity.payment.biz.dal.entity;

import com.youyu.cardequity.payment.biz.component.strategy.sqlqueryengine.SqlQueryEngineParseStrategy;
import com.youyu.cardequity.payment.dto.SqlQueryEngineResponseDto;
import com.youyu.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

import static com.youyu.cardequity.common.base.bean.CustomHandler.getBeanInstance;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2019年4月22日 10:00:00
 * @work sql查询引擎
 */
@Setter
@Getter
public class SqlQueryEngine extends BaseEntity<String> {

    private String id;

    private String serviceFlag;

    private String code;

    private String name;

    private String remark;

    private String initSql;

    private String sqlTemplate;

    private List<String> sqlWhereConditionTemplate;

    private List<String> sqlHavingConditionTemplate;

    private String parseType;

    private String dtoName;

    public SqlQueryEngine() {
    }

    public SqlQueryEngineResponseDto getSqlQueryEngineResponse(Map<String, String> paramMap) {
        SqlQueryEngineResponseDto sqlQueryEngineResponse = new SqlQueryEngineResponseDto();
        String realSql = getRealSql(paramMap);
        sqlQueryEngineResponse.setRealSql(realSql);
        sqlQueryEngineResponse.setDtoName(dtoName);
        return sqlQueryEngineResponse;
    }

    private String getRealSql(Map<String, String> paramMap) {
        SqlQueryEngineParseStrategy sqlQueryEngineStrategy = getBeanInstance(SqlQueryEngineParseStrategy.class, parseType);
        return sqlQueryEngineStrategy.parse(this, paramMap);
    }
}
