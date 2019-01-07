package com.youyu.cardequity.payment.biz.dal.dao;

import com.youyu.cardequity.common.base.annotation.SpringBean;
import com.youyu.cardequity.payment.biz.dal.entity.ExceptionLog;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月10日 下午10:00:00
 * @work 异常日志信息Mapper
 */
@SpringBean
public interface ExceptionLogMapper {

    /**
     * 插入
     *
     * @param exceptionLog
     */
    void insertSelective(ExceptionLog exceptionLog);
}
