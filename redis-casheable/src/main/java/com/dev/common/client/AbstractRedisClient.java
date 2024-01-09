package com.dev.common.client;


import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

abstract class AbstractRedisClient {

    protected final RedisTemplate<String, Object> redisTemplate;
    protected final ValueOperations<String, Object> valueOperations;

    AbstractRedisClient(RedisTemplate<String, Object> redisTemplate) {
	if (redisTemplate != null) {
	    this.redisTemplate = redisTemplate;
	    this.valueOperations = redisTemplate.opsForValue();
	} else {
	    this.redisTemplate = null;
	    this.valueOperations = null;
	}
    }

    public abstract void setValue(String key, Object value);

    public abstract void setValue(String key, Object value, int expireSeconds);

    public abstract <T> T getValue(String key);

    public abstract void deleteValue(String key);

    public abstract ValueOperations<String, Object> getValueOperations();

    public abstract Set<String> getKeys(String pattern);

}