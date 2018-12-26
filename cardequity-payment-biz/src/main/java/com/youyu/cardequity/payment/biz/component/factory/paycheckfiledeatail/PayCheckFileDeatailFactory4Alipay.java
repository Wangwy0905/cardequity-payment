package com.youyu.cardequity.payment.biz.component.factory.paycheckfiledeatail;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayDataDataserviceBillDownloadurlQueryModel;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.csvreader.CsvReader;
import com.youyu.cardequity.common.base.annotation.StatusAndStrategyNum;
import com.youyu.cardequity.payment.biz.component.properties.AlipayProperties;
import com.youyu.cardequity.payment.dto.PayCheckFileDeatailDto;
import com.youyu.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.alibaba.fastjson.JSON.toJSONString;
import static com.youyu.cardequity.common.base.util.DateUtil.*;
import static com.youyu.cardequity.payment.biz.help.util.AlipayFileUtil.downloadBill;
import static com.youyu.cardequity.payment.biz.help.util.FileUtil.unZipFile;
import static com.youyu.cardequity.payment.enums.PaymentResultCodeEnum.ALIPAY_BILL_DOWNLOAD_FAILED;
import static com.youyu.cardequity.payment.enums.PaymentResultCodeEnum.ALIPAY_BILL_DOWNLOAD_URL_FAILED;
import static java.io.File.separator;
import static java.nio.charset.Charset.forName;
import static org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace;
import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.commons.lang3.time.DateUtils.addDays;

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

    @Override
    public void createPayCheckFileDeatail(PayCheckFileDeatailDto payCheckFileDeatailDto) {
        String billDownloadUrl = getBillDownloadUrl(payCheckFileDeatailDto);

        String filePath = alipayProperties.getBillFilePath() + separator + payCheckFileDeatailDto.getBillDate() + separator;
        String fileNameZip = filePath + payCheckFileDeatailDto.getBillDate() + ".zip";
        downloadBill(billDownloadUrl, fileNameZip);

        unZipFile(fileNameZip, filePath, "GBK");

        parseCsv2PayCheckFileDeatails(filePath, "业务明细.csv");
    }

    private void parseCsv2PayCheckFileDeatails(String filePath, String suffix) {
        File file = new File(filePath);
        String fileName = file.list((dir, name) -> endsWith(name, suffix))[0];
        CsvReader csvReader = null;
        try {
            csvReader = new CsvReader(fileName, ',', forName("GBK"));
            csvReader.readHeaders();
            while (csvReader.readRecord()) {
                String record = csvReader.getRawRecord();
                if (startsWith(record, "#") || startsWith(record, "支付宝")) {
                    continue;
                }
                
            }
        } catch (Exception e) {
            // TODO: 2018/12/26
            throw new BizException("");
        } finally {
            csvReader.close();
        }
    }

    private String getBillDownloadUrl(PayCheckFileDeatailDto payCheckFileDeatailDto) {
        protectBillDate(payCheckFileDeatailDto);
        AlipayDataDataserviceBillDownloadurlQueryRequest alipayDataDataserviceBillDownloadurlQueryRequest = getAlipayDataDataserviceBillDownloadurlQueryRequest(payCheckFileDeatailDto);
        try {
            AlipayDataDataserviceBillDownloadurlQueryResponse alipayDataDataserviceBillDownloadurlQueryResponse = alipayClient.execute(alipayDataDataserviceBillDownloadurlQueryRequest);
            boolean billFlag = alipayDataDataserviceBillDownloadurlQueryResponse.isSuccess();
            if (!billFlag) {
                log.info("支付宝对账单根据参数:[{}]获取下载地址调用失败信息:[{}]!", toJSONString(payCheckFileDeatailDto), toJSONString(alipayDataDataserviceBillDownloadurlQueryResponse));
                throw new BizException(ALIPAY_BILL_DOWNLOAD_URL_FAILED);
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
