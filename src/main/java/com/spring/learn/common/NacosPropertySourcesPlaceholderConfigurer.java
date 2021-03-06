package com.spring.learn.common;

import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author J.zhu
 * @date 2019/7/2
 */
@Slf4j
public class NacosPropertySourcesPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {

    /**
     * 初始化配置文件dataId
     */
    private static final String INIT_CONFIG_DATA_ID = "application";
    /**
     * 初始化配置文件Group
     */
    private static final String INIT_CONFIG_GROUP = "DEFAULT_GROUP";
    /**
     * 初始化配置超时时间设置为10秒
     */
    private static final Long INIT_CONFIG_TIMEOUT = 10000L;

    @Nullable
    private Environment environment;
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected Properties mergeProperties() throws IOException {

        String active = this.environment.getActiveProfiles()[0];

        log.info("project properties active ：{}",active);

        // 加载配置文件
        Properties properties = loadLocalProperties(active);

        return properties;
    }

    /**
     * 加载配置文件
     * @return Properties 本地配置
     */
    private static Properties loadLocalProperties(String active){
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"),new ClassPathResource("application-"+active+".yml"));
        // 本地配置文件
        Properties localProperties = yaml.getObject();
        // 获取 Nacos serverAddr
        if(localProperties == null){
            log.error("初始化配置文件application.yml获取失败！");
        }
        assert localProperties != null;
        String serverAddr = localProperties.getProperty("nacos.config.server-addr");
        // 获取 Nacos namespace
        String namespace = localProperties.getProperty("nacos.config.namespace");
        // 加载 Nacos 远程配置文件
        Properties nacosProps = loadNacosConfig(serverAddr, namespace);
        // 合并本地和Nacos远程配置文件
        return mergeProperties(localProperties,nacosProps);
    }


    /**
     * 加载Nacos远程配置文件
     * @param serverAddr nacos服务地址
     * @param nameSpace namespace
     * @return Properties nacos远程配置
     */
    private static Properties loadNacosConfig(String serverAddr,String nameSpace){
        Properties nacosProperties;
        try {
            Properties properties = new Properties();
            properties.setProperty("namespace",nameSpace);
            properties.setProperty("serverAddr",serverAddr);
            ConfigService configService = ConfigFactory.createConfigService(properties);

            String config = configService.getConfig(INIT_CONFIG_DATA_ID, INIT_CONFIG_GROUP, INIT_CONFIG_TIMEOUT);
            YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
            yaml.setResources(new ByteArrayResource(config.getBytes()));
            nacosProperties = yaml.getObject();
        } catch (NacosException e) {
            throw new RuntimeException(String.format("Load Nacos:%s failed!",serverAddr));
        }
        return nacosProperties;
    }

    /**
     * 合并本地配置文件和Nacos远程配置
     * @param localProperties 本地配置
     * @param nacosProps nacos远程配置
     * @return Properties 合并后的配置
     */
    private static Properties mergeProperties(Properties localProperties, Properties nacosProps) {
        localProperties.putAll(nacosProps);
        return localProperties;
    }
}
