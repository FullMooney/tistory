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

@Slf4j
@EnableCaching
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {

    public static final String CKEY = "CKEY";

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
		    .withInitialCacheConfigurations(comConfigurationMap())
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

    private Map<String, RedisCacheConfiguration> comConfigurationMap() {
        Map<String, RedisCacheConfiguration> map = new TreeMap<>();

	map.put(CKEY, forJsonConfig().entryTtl(Duration.ofSeconds(60)));

        return map;
    }

    @Override
    public CacheErrorHandler errorHandler() {
	return new CacheExceptionHandler();
    }

}
