package com.youyu.cardequity.payment.biz.dal.dao;

import com.youyu.cardequity.payment.biz.dal.entity.SqlQueryEngine;
import org.apache.ibatis.annotations.Param;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2019年4月22日 10:00:00
 * @work sql查询引擎 Mapper
 */
public interface SqlQueryEngineMapper {

    /**
     * 根据serviceFlag和code查询
     *
     * @param serviceFlag
     * @param code
     * @return
     */
    SqlQueryEngine getByServiceFlagCode(@Param("serviceFlag") String serviceFlag, @Param("code") String code);
}
