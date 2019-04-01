package com.youyu.cardequity.payment.biz.help.util;

import com.csvreader.CsvReader;
import com.youyu.cardequity.common.base.tuple2.Tuple2;
import com.youyu.cardequity.payment.biz.help.bill.AlipayBill;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.youyu.cardequity.common.base.util.CloseableUtil.close;
import static com.youyu.cardequity.payment.biz.help.constant.AlipayConstant.ALIPAY_NAME;
import static com.youyu.cardequity.payment.biz.help.constant.SymbolConstant.*;
import static java.nio.charset.Charset.forName;
import static org.apache.commons.lang3.StringUtils.*;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付宝文件工具类
 */
public class AlipayFileUtil {

    /**
     * 支付宝官方提供:部分修改
     *
     * @param urlStr
     * @param fileName
     */
    public static void downloadAlipayBill(String urlStr, String fileName) {
        protectAlipayDir(fileName);

        HttpURLConnection httpUrlConnection = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            URL url = new URL(urlStr);
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setConnectTimeout(5 * 1000);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setRequestProperty("Charsert", CHARSERT_GBK);
            httpUrlConnection.connect();
            inputStream = httpUrlConnection.getInputStream();

            byte[] temp = new byte[1024];
            int b;
            fileOutputStream = new FileOutputStream(new File(fileName));
            while ((b = inputStream.read(temp)) != -1) {
                fileOutputStream.write(temp, 0, b);
                fileOutputStream.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            close(fileOutputStream, inputStream);

            if (httpUrlConnection != null) {
                httpUrlConnection.disconnect();
            }
        }
    }

    /**
     * 保护文件名对应目录的存在性
     *
     * @param fileName
     */
    public static void protectAlipayDir(String fileName) {
        if (isBlank(fileName)) {
            throw new RuntimeException("文件名不能为空!");
        }

        int lastIndex = fileName.lastIndexOf("/");
        String filePathDir = substring(fileName, 0, lastIndex);

        File file = new File(filePathDir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 将Csv文件的数据按行读取需要的数据添加到二元组里面
     *
     * @param filePath
     * @param suffix
     * @return
     */
    public static Tuple2<String, List<AlipayBill>> parseAlipayCsv2DataList(String filePath, String suffix) {
        List<AlipayBill> datas = new ArrayList<>();
        File file = new File(filePath);
        String fileName = file.list((dir, name) -> endsWith(name, suffix))[0];
        String fileNamePath = filePath + fileName;
        CsvReader csvReader = null;
        try {
            csvReader = new CsvReader(fileNamePath, COMMA.charAt(0), forName(CHARSERT_GBK));
            csvReader.readHeaders();
            while (csvReader.readRecord()) {
                String record = csvReader.getRawRecord();
                if (startsWith(record, POUND_SIGN) || startsWith(record, ALIPAY_NAME)) {
                    continue;
                }
                datas.add(new AlipayBill(csvReader));
            }
            return new Tuple2<>(fileName, datas);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            csvReader.close();
        }
    }
}
