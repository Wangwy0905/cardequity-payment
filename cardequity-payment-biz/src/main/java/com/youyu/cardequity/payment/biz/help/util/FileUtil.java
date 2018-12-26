package com.youyu.cardequity.payment.biz.help.util;

import com.csvreader.CsvReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static java.io.File.separator;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 文件工具类
 */
public class FileUtil {

    public static void main(String[] args) throws IOException {
        unZipFile("/Users/panqingqing/Downloads/对账单/alipay/2018-12-11/2018-12-11.zip", "/Users/panqingqing/Downloads/对账单/alipay/2018-12-11", "GBK");

        CsvReader csvReader = new CsvReader("/Users/panqingqing/Downloads/对账单/fund_bill_trade2018-12-19/20881021769041700156_20181219_业务明细.csv", ',', Charset.forName("GBK"));
        // 读表头
        csvReader.readHeaders();
        int headerCount = csvReader.getHeaderCount();
        while (csvReader.readRecord()) {
            // 读一整行
            System.out.println(csvReader.getRawRecord());
//            int columnCount = csvReader.getColumnCount();
//            for (int i = 0; i < columnCount; i++) {
//                // 读这行的某一列
//                System.out.println(csvReader.get(i));
//            }
        }

    }

    public static void unZipFile(String fileName, String filePath, String charset) {
        File zipFile = new File(fileName);
        try {
            ZipFile zip = new ZipFile(zipFile, Charset.forName(charset));
            String name = zip.getName().substring(zip.getName().lastIndexOf('\\') + 1, zip.getName().lastIndexOf('.'));
            File pathFile = new File(filePath);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }

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
