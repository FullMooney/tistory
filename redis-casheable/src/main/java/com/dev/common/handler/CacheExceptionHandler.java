package com.dev.common.handler;

import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * [클래스 한글명]
 *
 * <pre>
 * [클래스 개요]
 * </pre>
 * @author "user name"
 * @history
 * "user name"   2023. 5. 17.  [변경내용상세 기술]
*/
@Slf4j
public class CacheExceptionHandler implements CacheErrorHandler {

    /**
     * [메소드 한글명]
     *
     * <pre>
     * [메소드 개요]
     * </pre>
     *
     * @param exception
     * @param cache
     * @param key
     * @algorithm
     * <pre>
     * [처리로직 설명]
     * </pre>
     */
    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        log.error("{}", exception.getMessage());
        log.error("{}", exception.toString());
    }

    /**
     * [메소드 한글명]
     *
     * <pre>
     * [메소드 개요]
     * </pre>
     *
     * @param exception
     * @param cache
     * @param key
     * @param value
     * @algorithm
     * <pre>
     * [처리로직 설명]
     * </pre>
     */
    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        log.error("{}", exception.getMessage());
        log.error("{}", exception.toString());
    }

    /**
     * [메소드 한글명]
     *
     * <pre>
     * [메소드 개요]
     * </pre>
     *
     * @param exception
     * @param cache
     * @param key
     * @algorithm
     * <pre>
     * [처리로직 설명]
     * </pre>
     */
    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        log.error("{}", exception.getMessage());
        log.error("{}", exception.toString());
    }

    /**
     * [메소드 한글명]
     *
     * <pre>
     * [메소드 개요]
     * </pre>
     *
     * @param exception
     * @param cache
     * @algorithm
     * <pre>
     * [처리로직 설명]
     * </pre>
     */
    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        log.error("{}", exception.getMessage());
        log.error("{}", exception.toString());
    }

}
