package com.youyu.cardequity.payment.biz.help.util;

import com.csvreader.CsvReader;
import com.youyu.cardequity.common.base.tuple2.Tuple2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.youyu.cardequity.payment.biz.help.constant.AlipayConstant.ALIPAY_NAME;
import static com.youyu.cardequity.payment.biz.help.constant.SymbolConstant.*;
import static java.io.File.separator;
import static java.nio.charset.Charset.forName;
import static org.apache.commons.lang3.StringUtils.endsWith;
import static org.apache.commons.lang3.StringUtils.startsWith;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 文件工具类
 */
public class FileUtil {

    public static Tuple2<String, List<String>> parseCsv2DataList(String filePath, String suffix) {
        List<String> datas = new ArrayList<>();
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
                datas.add(record);
            }
            return new Tuple2<>(fileName, datas);
        } catch (Exception e) {
            // TODO: 2018/12/27
            throw new RuntimeException(e);
        } finally {
            csvReader.close();
        }
    }

    // TODO: 2018/12/27
    public static void unZipFile(String fileName, String filePath, String charset) {
        File zipFile = new File(fileName);
        try {
            ZipFile zip = new ZipFile(zipFile, forName(charset));
            for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = zip.getInputStream(entry);
                String outPath = (filePath + separator + zipEntryName).replaceAll("\\*", "/");

                File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
                if (!file.exists()) {
                    file.mkdirs();
                }
                if (new File(outPath).isDirectory()) {
                    continue;
                }

                FileOutputStream out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                in.close();
                out.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
