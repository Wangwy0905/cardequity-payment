package com.youyu.cardequity.payment.biz.test;

import com.youyu.common.generator.ConfigurationBuilder;
import com.youyu.common.generator.model.YyGeneratorConfig;
import com.youyu.common.generator.model.YyTableConfig;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author superwen
 * @date 2018/10/9 下午2:33
 */
public class ConfigurationBuilderOracleTest {


    public static void main(String[] args) throws InterruptedException, SQLException, InvalidConfigurationException, IOException {
        /**
         * 初始化 db oracleDB.sql
         */
        oracle();
    }

    public static void oracle() throws InvalidConfigurationException, InterruptedException, SQLException, IOException {
//        Generator.startDB();
        YyGeneratorConfig config = new YyGeneratorConfig();
        config.setTargetProject("/Users/panqingqing/yuWorkspaces/creditcardworkspaces/cardequity-payment/data");
        config.setBasePackage("com.youyu.cardequity.payment.biz");
        config.setAppId("cardequity-payment");
        config.setGenerateMapperXml(true);
        JDBCConnectionConfiguration connectionConfiguration = new JDBCConnectionConfiguration();
        connectionConfiguration.setDriverClass("com.mysql.jdbc.Driver");
        connectionConfiguration.setConnectionURL("jdbc:mysql://192.168.1.193:3306/cardequity_payment?useUnicode=true&amp;characterEncoding=UTF-8");
        connectionConfiguration.setUserId("cardequity_payment");
        connectionConfiguration.setPassword("cardequity_payment");
        config.setJdbcConnectionConfiguration(connectionConfiguration);

        YyTableConfig t1 = new YyTableConfig();
        t1.setTableName("TB_PAY_CHECK_DEATAIL%");
//        t1.setPkColumn("id");
        t1.setSqlStatement("JDBC");
//        t1.setSqlStatement("select SEQ_{1}.nextval from dual");
//        ColumnOverride columnOverride = new ColumnOverride("STATUS");
//        columnOverride.setJavaType("com.youyu.common.enums.WslStatus");
//        t1.addColumnOverride(columnOverride);
        config.addTableConfig(t1);


//        YyTableConfig t2 = new YyTableConfig();
//        t2.setTableName("WSL_STUDENT");
//        t2.setPkColumn("stu_id");
//        t2.setSqlStatement("select SEQ_WSL_STUDENT.nextval from dual");
//        ColumnOverride columnOverride = new ColumnOverride("STATUS");
//        columnOverride.setJavaType("com.youyu.common.enums.WslStatus");
//        t2.addColumnOverride(columnOverride);
//        config.addTableConfig(t2);

        Configuration configuration = ConfigurationBuilder.build(config);


        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
//        ConfigurationParser cp = new ConfigurationParser(warnings);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, callback, warnings);
        myBatisGenerator.generate(null);
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}
