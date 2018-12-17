package com.youyu.cardequity.payment.biz.test;

import com.youyu.cardequity.common.spring.base.BaseSpringBootTest;
import com.youyu.cardequity.payment.biz.CardequityPaymentApplication;
import com.youyu.cardequity.payment.biz.controller.CompanyInfoController;
import com.youyu.cardequity.payment.biz.dal.entity.CompanyInfo;
import com.youyu.cardequity.payment.biz.service.CompanyInfoService;
import com.youyu.cardequity.payment.dto.CompanyInfoDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CardequityPaymentApplication.class)
public class CompanyInfoServiceTest extends BaseSpringBootTest {

    @Autowired
    private CompanyInfoController companyInfoController;

    @Test
    public void testAdd() {
        CompanyInfoDto companyInfoDto = new CompanyInfoDto();
        companyInfoDto.setCompanyId("000002");
        companyInfoDto.setCompanyName("测试公司信息");
        companyInfoDto.setRemark("测试");
        companyInfoController.add(companyInfoDto);
    }

    @Test
    public void testDelete() {
        CompanyInfoDto companyInfoDto = new CompanyInfoDto();
        companyInfoDto.setCompanyId("000002");
        companyInfoController.delete(companyInfoDto);
    }

    @Test
    public void testUpdate() {
        CompanyInfoDto companyInfoDto = new CompanyInfoDto();
        companyInfoDto.setCompanyId("000002");
        companyInfoDto.setCompanyName("测试xxxx");
        companyInfoDto.setRemark("测试xxxx");
        companyInfoController.add(companyInfoDto);
    }

    @Test
    public void testGet() {
    }
}
