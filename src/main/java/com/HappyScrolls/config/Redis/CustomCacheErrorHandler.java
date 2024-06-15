package com.HappyScrolls.config.Redis;

import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

public class CustomCacheErrorHandler implements CacheErrorHandler {

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        System.out.println("레디스 접속 오류!2123");
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        System.out.println("레디스 접속 오류!2");

    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        System.out.println("레디스 접속 오류!3");

    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        System.out.println("레디스 접속 오류!4");

    }
}
