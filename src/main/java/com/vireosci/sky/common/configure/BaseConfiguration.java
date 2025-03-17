package com.vireosci.sky.common.configure;

import com.vireosci.sky.common.property.SecurityProperties;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

/// 基础配置
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class BaseConfiguration implements WebMvcConfigurer
{
    /// Validator 配置
    @Bean
    public Validator validator()
    {
        var validator = Validation
                .byDefaultProvider()
                .configure()
                .addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory();

        try (validator) { return validator.getValidator(); }
    }

    /// 关闭跨域拦截
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry
                .addMapping("/**")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Bean
    public MessageSource messageSource()
    {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages"); // 指定 messages 目录
        messageSource.setDefaultEncoding("UTF-8"); // 确保 UTF-8 编码
        messageSource.setDefaultLocale(Locale.CHINA);
        return messageSource;
    }
}
