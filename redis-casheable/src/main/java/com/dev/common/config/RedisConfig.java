package com.dev.common.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RedisConfig {

    @Value("${spring.redis.url}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}") // 패스워드 설정시
    private String password;

    @Value("${lettuce.connect-timeout:500}")
    private int connectTimeout;

    @Value("${lettuce.command-timeout:500}")
    private int commandTimeout;
    
    @Value("${spring.redis.database}")
    private int database;


    @Bean
    RedisConnectionFactory redisConnectionFactory() {

        try {
            
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setPort(port);
            redisStandaloneConfiguration.setHostName(host);
	    redisStandaloneConfiguration.setPassword(password); // 패스워드 설정시
            redisStandaloneConfiguration.setDatabase(database);

            LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
              .clientOptions(
                  ClientOptions.builder()
                      .socketOptions(
                          SocketOptions.builder()
                              .connectTimeout(Duration.ofMillis(connectTimeout))
                              .build())
                      .build())
              .commandTimeout(Duration.ofMillis(commandTimeout))
              .build();

            LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfiguration);

            lettuceConnectionFactory.afterPropertiesSet();
            lettuceConnectionFactory.setValidateConnection(false);

            return lettuceConnectionFactory;
        } catch(Exception e) {
            log.error("", e);

            return null;
        }
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate() {
        try {
            RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory  (redisConnectionFactory());
            redisTemplate.setKeySerializer      (new StringRedisSerializer());
            redisTemplate.setValueSerializer    (new GenericJackson2JsonRedisSerializer());
            redisTemplate.setHashKeySerializer  (new StringRedisSerializer());
            redisTemplate.setHashValueSerializer(new StringRedisSerializer());

            return redisTemplate;
        } catch(Exception e) {
            log.error("redisTemplate create Exception", e);
        }

        return null;
    }

}
