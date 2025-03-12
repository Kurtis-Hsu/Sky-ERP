package com.vireosci.sky.common.configure;

import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

/// 数据库相关配置
@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
public class DatabaseConfiguration
{
    private static final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

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

    /// 使用数据库的 `now()` 函数获取当前时间
    @Bean
    public DateTimeProvider auditingDateTimeProvider()
    {
        return new DateTimeProvider()
        {
            @PersistenceContext
            private EntityManager entityManager;

            @Override
            @Nonnull
            public Optional<TemporalAccessor> getNow()
            {
                var now = (Instant) entityManager.createNativeQuery("SELECT now()").getSingleResult();
                return Optional.of(now);
            }
        };
    }
}
