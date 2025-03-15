package com.vireosci.sky.common.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

/// 数据库相关配置
@Configuration
@EnableJpaAuditing(
        modifyOnCreate = false, dateTimeProviderRef = "auditingDateTimeProvider", auditorAwareRef = "auditorAware"
)
@EnableJpaRepositories("com.vireosci.sky.repository")
public class DatabaseConfiguration
{
    /// 配置 `Redis` 模板
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
    {
        var redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    /// 使用 [LocalDateTime#now()] 提供当前时间
    @Bean
    public DateTimeProvider auditingDateTimeProvider() { return CurrentDateTimeProvider.INSTANCE; }

    /// 向 Spring 提供默认的 [AuditorAware]
    @Bean
    public AuditorAware<String> auditorAware()
    {
        return () -> Optional
                .ofNullable(
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                )
                .map(auth -> auth.getPrincipal().toString());
    }
}
