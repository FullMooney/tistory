package com.dev.demo.service.impl;

import java.util.Date;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dev.common.config.RedisCacheConfig;
import com.dev.demo.service.CacheService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CacheServiceImpl implements CacheService {

    @SuppressWarnings("deprecation")
    @Cacheable(cacheNames = RedisCacheConfig.CKEY, key = "#key", unless = "#result==null")
    @Override
    public String getCacheableVal(String key) {
	// TODO DB 조회 또는 API 호출

	Date dt = new Date();
	int sec = dt.getSeconds();
	log.debug("{}", sec);
	for (int i = 0; i < 100; i++) {
	    if (i == sec) { // 호출 시점의 second와 일치하는 값을 출력하고 리턴
		log.debug("i== {}", i);
		return String.valueOf(i);
	    }
	}
	return null;
    }

}
