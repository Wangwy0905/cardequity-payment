package com.youyu.cardequity.payment.biz.test;

import com.youyu.cardequity.common.spring.base.BaseSpringBootTest;
import com.youyu.cardequity.payment.biz.CardequityPaymentApplication;
import com.youyu.cardequity.payment.biz.dal.dao.PayChannelInfoMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayChannelInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CardequityPaymentApplication.class)
public class PayChannelInfoMapperTest extends BaseSpringBootTest {

    @Autowired
    private PayChannelInfoMapper payChannelInfoMapper;

    @Test
    public void test() {
        PayChannelInfo payChannelInfo = payChannelInfoMapper.getById("000001");
        System.out.println(payChannelInfo);
    }
}
