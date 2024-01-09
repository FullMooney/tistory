package com.dev.common.config;

import java.time.Duration;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import com.dev.common.handler.CacheExceptionHandler;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;

import lombok.extern.slf4j.Slf4j;

/**
 * [클래스 한글명]
 *
 * <pre>
 * [클래스 개요]
 * </pre>
 * @author "user name"
 * @history
 * "user name"   2023. 5. 16.  [변경내용상세 기술]
*/
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
@EnableCaching
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {

    public static final String MESSAGE = "MESSAGE";

    private final ObjectMapper objectMapper;
    private final RedisConnectionFactory redisConnectionFactory;


    private CacheKeyPrefix cacheKeyPrefix;

    @Value("${spring.cache.redis.key-prefix:com}")
    private String springCacheRedisKeyPrefix;

    @Value("${spring.cache.redis.use-key-prefix:true}")
    private boolean springCacheRedisUseKeyPrefix;


    public RedisCacheConfig(ObjectMapper objectMapper, RedisConnectionFactory redisConnectionFactory) {
        this.objectMapper = objectMapper;
        this.redisConnectionFactory = redisConnectionFactory;
    }


    @PostConstruct
    private void onPostConstructor() {
        if (springCacheRedisUseKeyPrefix && StringUtils.hasText(springCacheRedisKeyPrefix)) {
            cacheKeyPrefix = cacheName -> springCacheRedisKeyPrefix.trim() + "::" + cacheName + "::";
        } else {
            cacheKeyPrefix = CacheKeyPrefix.simple();
        }
    }


    @Bean
    RedisCacheManager redisCacheManager() {
        try {
            RedisCacheConfiguration redisCacheConfiguration = forJsonConfig();

            return RedisCacheManager.RedisCacheManagerBuilder
                    .fromConnectionFactory(redisConnectionFactory)
                    .cacheDefaults(redisCacheConfiguration)
                    .withInitialCacheConfigurations(hroConfigurationMap())
                    .build();
        } catch(Exception e) {
            log.error("redisCacheManager create Exception", e);
        }

        return null;
    }

    private RedisCacheConfiguration forJsonConfig() {
        ObjectMapper objectMapperForRedisCache = objectMapper.copy();
        objectMapperForRedisCache.setSerializationInclusion(Include.NON_NULL);
        objectMapperForRedisCache.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
        objectMapperForRedisCache.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapperForRedisCache.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);

        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer(objectMapperForRedisCache);

        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith  (RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer))
                .computePrefixWith(cacheKeyPrefix)
                ;
    }

    private Map<String, RedisCacheConfiguration> hroConfigurationMap() {
        Map<String, RedisCacheConfiguration> map = new TreeMap<>();

        map.put(MESSAGE, forJsonConfig().entryTtl(Duration.ofHours(1L)));

        return map;
    }

    /**
     * 에러 핸들러
     *
     * <pre>
     * @Cacheable, @CacheEvict annotation 사용시 redis 오류 발생시 exception handling하기 위해
     * exception handler 등록 메소드
     * </pre>
     *
     * @return
     * @algorithm
     * <pre>
     * [처리로직 설명]
     * </pre>
     */
    @Override
    public CacheErrorHandler errorHandler() {
	return new CacheExceptionHandler();
    }

}
