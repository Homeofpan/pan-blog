package com.pan.config;

import com.github.pagehelper.PageHelper;
import com.github.tobato.fastdfs.FdfsClientConfig;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

import java.util.Properties;

@Configuration
@Import(FdfsClientConfig.class)
public class MyConfig {

    //配置mybatis的分页插件pageHelper
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties props = new Properties();
        //设置数据库类型
        props.setProperty("dialect", "mysql");
        // 表示支持从接口中读取pageNum和pageSize
        props.setProperty("supportMethodsArguments", "true");
        pageHelper.setProperties(props);
        return pageHelper;
    }

}
