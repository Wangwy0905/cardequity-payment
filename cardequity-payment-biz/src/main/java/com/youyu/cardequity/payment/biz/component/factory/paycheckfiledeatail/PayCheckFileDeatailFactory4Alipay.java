package com.youyu.cardequity.payment.biz.component.factory.paycheckfiledeatail;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayDataDataserviceBillDownloadurlQueryModel;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.common.base.tuple2.Tuple2;
import com.youyu.cardequity.common.spring.service.BatchService;
import com.youyu.cardequity.payment.biz.component.properties.AlipayProperties;
import com.youyu.cardequity.payment.biz.dal.dao.PayCheckFileDeatailMapper;
import com.youyu.cardequity.payment.biz.dal.entity.PayCheckFileDeatail;
import com.youyu.cardequity.payment.biz.help.bill.AlipayBill;
import com.youyu.cardequity.payment.dto.PayCheckFileDeatailDto;
import com.youyu.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.JSON.toJSONString;
import static com.youyu.cardequity.common.base.util.DateUtil.*;
import static com.youyu.cardequity.common.base.util.StringUtil.eq;
import static com.youyu.cardequity.payment.biz.help.constant.AlipayConstant.ALIPAY_BILL_BUSIN_TYPE_TRADE;
import static com.youyu.cardequity.payment.biz.help.constant.AlipayConstant.ALIPAY_BILL_FILE_NAME;
import static com.youyu.cardequity.payment.biz.help.constant.BusinessConstant.BUSIN_TYPE_REFUND;
import static com.youyu.cardequity.payment.biz.help.constant.BusinessConstant.BUSIN_TYPE_TRADE;
import static com.youyu.cardequity.payment.biz.help.constant.SymbolConstant.*;
import static com.youyu.cardequity.payment.biz.help.util.AlipayFileUtil.downloadAlipayBill;
import static com.youyu.cardequity.payment.biz.help.util.AlipayFileUtil.parseAlipayCsv2DataList;
import static com.youyu.cardequity.payment.biz.help.util.FileUtil.unZip2File;
import static com.youyu.cardequity.payment.enums.PaymentResultCodeEnum.ALIPAY_BILL_DOWNLOAD_FAILED;
import static com.youyu.cardequity.payment.enums.PaymentResultCodeEnum.ALIPAY_BILL_DOWNLOAD_URL_FAILED;
import static java.io.File.separator;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.time.DateUtils.addDays;
import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 对账文件明细信息对账工厂:支付宝
 */
@Slf4j
@StatusAndStrategyNum(superClass = PayCheckFileDeatailFactory.class, number = "1", describe = "支付宝对账文件明细信息对账工厂")
@Component
public class PayCheckFileDeatailFactory4Alipay extends PayCheckFileDeatailFactory {

    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private BatchService batchService;

    @Override
    public void createPayCheckFileDeatail(PayCheckFileDeatailDto payCheckFileDeatailDto) {
        String billDownloadUrl = getBillDownloadUrl(payCheckFileDeatailDto);

        String filePath = alipayProperties.getBillFilePath() + separator + payCheckFileDeatailDto.getBillDate() + separator;
        String fileNameZip = filePath + payCheckFileDeatailDto.getBillDate() + DOT + ZIP;
        downloadAlipayBill(billDownloadUrl, fileNameZip);

        unZip2File(fileNameZip, filePath, CHARSERT_GBK);

        Tuple2<String, List<AlipayBill>> dataTuple2 = parseAlipayCsv2DataList(filePath, ALIPAY_BILL_FILE_NAME);

        batchDisposeDatas2PayCheckFileDeatails(dataTuple2, payCheckFileDeatailDto);
    }

    private void batchDisposeDatas2PayCheckFileDeatails(Tuple2<String, List<AlipayBill>> dataTuple2, PayCheckFileDeatailDto payCheckFileDeatailDto) {
        String fileName = dataTuple2.a;
        List<AlipayBill> alipayBills = dataTuple2.b;

        if (isEmpty(alipayBills)) {
            return;
        }

        List<PayCheckFileDeatail> payCheckFileDeatails = new ArrayList<>();
        for (AlipayBill alipayBill : alipayBills) {
            String type = alipayBill.getBusinType();
            String businType = eq(type, ALIPAY_BILL_BUSIN_TYPE_TRADE) ? BUSIN_TYPE_TRADE : BUSIN_TYPE_REFUND;
            payCheckFileDeatails.add(new PayCheckFileDeatail(alipayBill, payCheckFileDeatailDto, fileName, businType));
        }

        List<PayCheckFileDeatail> payCheckFileDeatailDeletes = payCheckFileDeatails.stream().map(payCheckFileDeatail -> new PayCheckFileDeatail(payCheckFileDeatail)).collect(toList());

        batchService.batchDispose(payCheckFileDeatailDeletes, PayCheckFileDeatailMapper.class, "deleteByTranceNoCheckDate");
        batchService.batchDispose(payCheckFileDeatails, PayCheckFileDeatailMapper.class, "insertSelective");
    }

    private String getBillDownloadUrl(PayCheckFileDeatailDto payCheckFileDeatailDto) {
        protectBillDate(payCheckFileDeatailDto);

        AlipayDataDataserviceBillDownloadurlQueryRequest alipayDataDataserviceBillDownloadurlQueryRequest = getAlipayDataDataserviceBillDownloadurlQueryRequest(payCheckFileDeatailDto);
        try {
            AlipayDataDataserviceBillDownloadurlQueryResponse alipayDataDataserviceBillDownloadurlQueryResponse = alipayClient.execute(alipayDataDataserviceBillDownloadurlQueryRequest);
            boolean billFlag = alipayDataDataserviceBillDownloadurlQueryResponse.isSuccess();
            if (!billFlag) {
                log.info("支付宝对账单根据参数:[{}]获取下载地址调用失败信息:[{}]!", toJSONString(payCheckFileDeatailDto), toJSONString(alipayDataDataserviceBillDownloadurlQueryResponse));
                throw new BizException(ALIPAY_BILL_DOWNLOAD_URL_FAILED.getCode(), ALIPAY_BILL_DOWNLOAD_URL_FAILED.getFormatDesc(alipayDataDataserviceBillDownloadurlQueryResponse.getSubMsg()));
            }
            String billDownloadUrl = alipayDataDataserviceBillDownloadurlQueryResponse.getBillDownloadUrl();
            log.info("支付宝对账单地址信息:[{}]", billDownloadUrl);
            return billDownloadUrl;
        } catch (AlipayApiException e) {
            log.error("支付宝对账单下载失败异常信息:[{}]", getFullStackTrace(e));
            throw new BizException(ALIPAY_BILL_DOWNLOAD_FAILED, e);
        }
    }

    private void protectBillDate(PayCheckFileDeatailDto payCheckFileDeatailDto) {
        String billDate = payCheckFileDeatailDto.getBillDate();
        if (isBlank(billDate)) {
            billDate = date2String(addDays(now(), -1), YYYY_MM_DD);
            payCheckFileDeatailDto.setBillDate(billDate);
        }
    }

    private AlipayDataDataserviceBillDownloadurlQueryRequest getAlipayDataDataserviceBillDownloadurlQueryRequest(PayCheckFileDeatailDto payCheckFileDeatailDto) {
        AlipayDataDataserviceBillDownloadurlQueryRequest alipayDataDataserviceBillDownloadurlQueryRequest = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        AlipayDataDataserviceBillDownloadurlQueryModel alipayDataDataserviceBillDownloadurlQueryModel = getAlipayDataDataserviceBillDownloadurlQueryModel(payCheckFileDeatailDto);
        alipayDataDataserviceBillDownloadurlQueryRequest.setBizModel(alipayDataDataserviceBillDownloadurlQueryModel);
        return alipayDataDataserviceBillDownloadurlQueryRequest;
    }

    private AlipayDataDataserviceBillDownloadurlQueryModel getAlipayDataDataserviceBillDownloadurlQueryModel(PayCheckFileDeatailDto payCheckFileDeatailDto) {
        AlipayDataDataserviceBillDownloadurlQueryModel alipayDataDataserviceBillDownloadurlQueryModel = new AlipayDataDataserviceBillDownloadurlQueryModel();
        alipayDataDataserviceBillDownloadurlQueryModel.setBillType("trade");
        alipayDataDataserviceBillDownloadurlQueryModel.setBillDate(payCheckFileDeatailDto.getBillDate());
        return alipayDataDataserviceBillDownloadurlQueryModel;
    }
}
