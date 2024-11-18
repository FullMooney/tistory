package com.dev.log.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

@Configuration
public class ObjectClientConfig {
    @Value("${minio.bucket.name}")
    private String bucket;

    @Value("${minio.url}")
    private String url;

    @Value("${minio.access.key}")
    private String key;

    @Value("${minio.access.secret}")
    private String secret;


    @Bean
    MinioClient minioClient() {
        MinioClient minioClient;

        minioClient = MinioClient.builder().endpoint(url).credentials(key, secret).build();

        return minioClient;
    }
}
