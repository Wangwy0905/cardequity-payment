package com.youyu.cardequity.payment.biz.help.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.apache.commons.lang3.StringUtils.substring;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 支付宝文件工具类
 */
public class AlipayFileUtil {

    // TODO: 2018/12/27

    /**
     * 支付宝官方提供
     *
     * @param urlStr
     * @param fileName
     */
    public static void downloadBill(String urlStr, String fileName) {
        protectDir(fileName);

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
            httpUrlConnection.setRequestProperty("Charsert", "UTF-8");
            httpUrlConnection.connect();
            inputStream = httpUrlConnection.getInputStream();
            byte[] temp = new byte[1024];
            int b;
            fileOutputStream = new FileOutputStream(new File(fileName));
            while ((b = inputStream.read(temp)) != -1) {
                fileOutputStream.write(temp, 0, b);
                fileOutputStream.flush();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (httpUrlConnection != null) {
                    httpUrlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保护文件名对应目录的存在性
     *
     * @param fileName
     */
    public static void protectDir(String fileName) {
        int lastIndex = fileName.lastIndexOf("/");
        String filePathDir = substring(fileName, 0, lastIndex);
        File file = new File(filePathDir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
