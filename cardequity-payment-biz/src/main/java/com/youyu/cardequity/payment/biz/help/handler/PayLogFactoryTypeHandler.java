package com.youyu.cardequity.payment.biz.help.handler;

import com.youyu.cardequity.common.orm.mybatis.handler.BaseString2StatusStrategyTypeHandler;
import com.youyu.cardequity.payment.biz.component.factory.PayLogFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 自定义类型
 */
@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({PayLogFactory.class})
public class PayLogFactoryTypeHandler extends BaseString2StatusStrategyTypeHandler {

    @Override
    public String getClassSimpleName() {
        return PayLogFactory.class.getSimpleName();
    }
}
