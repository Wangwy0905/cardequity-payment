package com.youyu.cardequity.payment.biz.test;

import com.youyu.cardequity.common.base.util.UuidUtil;
import com.youyu.cardequity.common.spring.base.BaseSpringBootTest;
import com.youyu.cardequity.payment.biz.CardequityPaymentApplication;
import com.youyu.cardequity.payment.biz.service.PayLogService;
import com.youyu.cardequity.payment.dto.PayLogDto;
import com.youyu.common.dto.BaseDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest(classes = CardequityPaymentApplication.class)
public class PayLogServiceImplTest extends BaseSpringBootTest {

    @Autowired
    private PayLogService payLogService;

    @Test
    public void test() {
        PayLogDto payLogDto = new PayLogDto();
        payLogDto.setOccurBalance(new BigDecimal("1"));
        payLogDto.setSubject("香蕉");
        payLogDto.setGoodsType("1");
        payLogDto.setClientId("11111");
        payLogDto.setAppSheetSerialNo(UuidUtil.uuid4NoRail());
        payLogDto.setCertificateNo("xxx");
        payLogDto.setCertificateType("xx");
        payLogDto.setTransAccountId("111");
        payLogDto.setRemark("支付宝支付");
        payLogDto.setPayChannelNo("000001");
        payLogDto.setInitDate(LocalDate.now());

        BaseDto baseDto = payLogService.pay(payLogDto);
        System.out.println(baseDto);
    }
}
