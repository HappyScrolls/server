package com.HappyScrolls.config.Redis;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisCacheConfig extends CachingConfigurerSupport {

    private final RedisConnectionFactory cf;
    @Override
    public CacheManager cacheManager() {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(Duration.ofMinutes(3L));

        return RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(cf)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }


//    @Override
//    public CacheErrorHandler errorHandler() {
//        return new CustomCacheErrorHandler();
//    }
}