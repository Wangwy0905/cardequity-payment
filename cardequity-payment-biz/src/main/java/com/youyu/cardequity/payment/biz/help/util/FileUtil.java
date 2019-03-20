package com.youyu.cardequity.payment.biz.help.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.youyu.cardequity.common.base.util.CloseableUtil.close;
import static java.io.File.separator;
import static java.nio.charset.Charset.forName;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 文件工具类
 */
public class FileUtil {

    /**
     * 把zip文件解压到filePath目录下面:支持中文
     *
     * @param fileName 文件名
     * @param filePath 文件路径
     * @param charset  字符集
     */
    public static void unZip2File(String fileName, String filePath, String charset) {
        File zipFile = new File(fileName);

        ZipFile zip = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            zip = new ZipFile(zipFile, forName(charset));
            for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements(); ) {
                ZipEntry zipEntry = entries.nextElement();
                String zipEntryName = zipEntry.getName();
                inputStream = zip.getInputStream(zipEntry);
                String outPath = (filePath + separator + zipEntryName).replaceAll("\\*", "/");

                File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
                if (!file.exists()) {
                    file.mkdirs();
                }
                if (new File(outPath).isDirectory()) {
                    continue;
                }

                fileOutputStream = new FileOutputStream(outPath);
                byte[] temp = new byte[1024];
                int b;
                while ((b = inputStream.read(temp)) > 0) {
                    fileOutputStream.write(temp, 0, b);
                    fileOutputStream.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            close(fileOutputStream, inputStream, zip);
        }
    }
}
