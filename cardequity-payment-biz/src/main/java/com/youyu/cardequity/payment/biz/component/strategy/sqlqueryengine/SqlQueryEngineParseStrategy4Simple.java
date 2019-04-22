package com.youyu.cardequity.payment.biz.component.strategy.sqlqueryengine;

import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.dal.entity.SqlQueryEngine;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.youyu.cardequity.payment.biz.help.util.FreemarkerUtil.parseString4Map;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.*;
import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2019年4月21日 10:00:00
 * @work sql查询引擎解析策略:简单策略
 */
@StatusAndStrategyNum(superClass = SqlQueryEngineParseStrategy.class, number = "0", describe = "简单解析策略")
@Component
public class SqlQueryEngineParseStrategy4Simple extends SqlQueryEngineParseStrategy {

    @Override
    public String parse(SqlQueryEngine sqlQueryEngine, Map<String, String> paramMap) {
        String sqlTemplate = replacePattern(sqlQueryEngine.getSqlTemplate(), "\t|\n", " ");

        List<String> conditionTemplates = sqlQueryEngine.getSqlWhereConditionTemplate();
        if (isEmpty(conditionTemplates)) {
            return sqlTemplate;
        }

        List<String> validConditionTemplates = getValidConditionTemplates(paramMap, conditionTemplates);
        if (isEmpty(validConditionTemplates)) {
            return sqlTemplate;
        }

        String join = join(validConditionTemplates, " ,");

        String sql = join(sqlTemplate, " where 1 = 1 ", join(validConditionTemplates, " ,"));
        return parseString4Map(sql, paramMap);
    }

    private List<String> getValidConditionTemplates(Map<String, String> paramMap, List<String> conditionTemplates) {
        List<String> validConditionTemplates = new ArrayList<>();
        for (String conditionTemplate : conditionTemplates) {
            int start = indexOf(conditionTemplate, "{") + 1;
            int end = indexOf(conditionTemplate, "}");
            String key = substring(conditionTemplate, start, end);

            if (nonNull(paramMap.get(key))) {
                validConditionTemplates.add(conditionTemplate);
            }
        }
        return validConditionTemplates;
    }

}
