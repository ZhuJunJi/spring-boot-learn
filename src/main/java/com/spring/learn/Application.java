package com.spring.learn;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.spring.learn.common.NacosPropertySourcesPlaceholderConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.TimeZone;

/**
 * @author Administrator
 */
@MapperScan("com.spring.learn.mapper")
@SpringBootApplication(scanBasePackages = "com.spring.learn")
@NacosPropertySource(dataId = "spring-boot-learn", autoRefreshed = true)
@Slf4j
public class Application {

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public static NacosPropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(ApplicationContext applicationContext) {

        NacosPropertySourcesPlaceholderConfigurer placeholder = new NacosPropertySourcesPlaceholderConfigurer();
        placeholder.setEnvironment(applicationContext.getEnvironment());
        return placeholder;
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonBuilderCustomizer() {
        return builder -> {
            builder.indentOutput(true);
            builder.timeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        };
    }
}
