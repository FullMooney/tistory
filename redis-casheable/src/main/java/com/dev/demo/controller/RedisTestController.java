package com.dev.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.common.client.CommonRedisClient;
import com.dev.demo.service.CacheService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/redis")
public class RedisTestController {
    
    private final CommonRedisClient redisClient;
    private final CacheService cacheService;

    RedisTestController(CommonRedisClient redisClient, CacheService cacheService) {
	this.redisClient = redisClient;
	this.cacheService = cacheService;
    }

    @PostMapping("/set-cache-val")
    public ResponseEntity<Void> setCacheVal(@RequestBody String key) {
        
	redisClient.setValue(key, "100", 60);

	return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    @GetMapping("/get-cache-val/{key}")
    public ResponseEntity<String> getCacheVal(@PathVariable(name = "key") String key) {

	String val = (String) redisClient.getValue(key);

	return ResponseEntity.ok(val);
    }

    @GetMapping("/get-cacheable-val/{key}")
    public ResponseEntity<String> getCacheableVal(@PathVariable(name = "key") String key) {

	String val = cacheService.getCacheableVal(key);
	log.debug("cached val is == {}", val);

	return ResponseEntity.ok(val);
    }

}
