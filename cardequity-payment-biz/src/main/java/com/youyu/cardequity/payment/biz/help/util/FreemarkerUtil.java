package com.youyu.cardequity.payment.biz.help.util;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.Map;

import static com.youyu.cardequity.common.base.util.CloseableUtil.close;
import static freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2019年4月4日 10:00:00
 * @work Freemarker工具类
 */
public class FreemarkerUtil {

    private static final String STRING_TEMPLATE = "stringTemplate";

    private static Configuration configuration = new Configuration(DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

    /**
     * 根据params参数解析param
     *
     * @param param
     * @param params
     * @return
     */
    public static String parseString4Map(String param, Map<String, String> params) {
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate(STRING_TEMPLATE, param);
        configuration.setTemplateLoader(stringTemplateLoader);
        StringWriter stringWriter = null;
        try {
            Template template = configuration.getTemplate(STRING_TEMPLATE, "utf-8");
            stringWriter = new StringWriter();
            template.process(params, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException("模板:" + param + "解析参数:" + params + "异常信息:" + e.getMessage(), e);
        } finally {
            close(stringWriter);
        }
    }
}
