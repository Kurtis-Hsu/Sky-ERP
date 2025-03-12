package com.vireosci.sky.common.configure;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/// 基础配置
@Configuration
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
}
