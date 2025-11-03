package com.janero.urlshortener.shared.infrastructure.config;

import java.time.Duration;
import java.util.Arrays;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableCaching
@Configuration
public class CacheConfig {

    public static final String URL_KEY_CACHE = "url_key";

    @Bean
    CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration config =
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(24))
                        .serializeValuesWith(RedisSerializationContext.SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer()));
        log.info(
                "[cache_config] Redis Cache Manager initialized with 24h TTL and polymorphic Jackson serialization...");
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(config).build();
    }

    @Bean
    KeyGenerator keyGenerator() {
        return (target, method, params) -> target + Arrays.toString(params);
    }

}
