package com.youyu.cardequity.payment.biz.service.impl;

import com.youyu.cardequity.payment.biz.dal.dao.SqlQueryEngineMapper;
import com.youyu.cardequity.payment.biz.dal.entity.SqlQueryEngine;
import com.youyu.cardequity.payment.biz.service.SqlQueryEngineService;
import com.youyu.cardequity.payment.dto.SqlQueryEngineResponseDto;
import com.youyu.common.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.youyu.cardequity.payment.biz.help.constant.SqlQueryConstant.CODE;
import static com.youyu.cardequity.payment.biz.help.constant.SqlQueryConstant.SERVICE_FLAG;
import static com.youyu.cardequity.payment.enums.PaymentResultCodeEnum.*;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2019年4月22日 10:00:00
 * @work sql查询引擎 service 实现
 */
@Service
public class SqlQueryEngineServiceImpl implements SqlQueryEngineService {

    @Autowired
    private SqlQueryEngineMapper sqlQueryEngineMapper;

    @Override
    public SqlQueryEngineResponseDto getSqlQueryEngine(Map<String, String> paramMap) {
        checkParamMap(paramMap);

        String serviceFlag = paramMap.get(SERVICE_FLAG);
        String code = paramMap.get(CODE);
        SqlQueryEngine sqlQueryEngine = sqlQueryEngineMapper.getByServiceFlagCode(serviceFlag, code);
        if (isNull(sqlQueryEngine)) {
            throw new BizException(QUERY_SQL_ENGINE_EXCEPTION);
        }

        return sqlQueryEngine.getSqlQueryEngineResponse(paramMap);
    }

    private void checkParamMap(Map<String, String> paramMap) {
        if (isBlank(paramMap.get(SERVICE_FLAG))) {
            throw new BizException(QUERY_SQL_SERVICE_FLAG_NOT_NULL);
        }

        if (isBlank(paramMap.get(CODE))) {
            throw new BizException(QUERY_SQL_CODE_NOT_NULL);
        }
    }


}
