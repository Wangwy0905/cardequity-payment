package com.youyu.cardequity.payment.biz.controller;

import com.youyu.cardequity.payment.api.SqlQueryEngineApi;
import com.youyu.cardequity.payment.biz.service.SqlQueryEngineService;
import com.youyu.cardequity.payment.dto.SqlQueryEngineResponseDto;
import com.youyu.common.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.youyu.common.api.Result.ok;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2019年4月22日 10:00:00
 * @work sql查询引擎 controller
 */
@RestController
@RequestMapping("/sqlQueryEngine")
public class SqlQueryEngineController implements SqlQueryEngineApi {

    @Autowired
    private SqlQueryEngineService sqlQueryEngineService;

    @Override
    @PostMapping("/getSqlQueryEngine")
    public Result<SqlQueryEngineResponseDto> getSqlQueryEngine(@RequestBody Map<String, String> paramMap) {
        return ok(sqlQueryEngineService.getSqlQueryEngine(paramMap));
    }
}
