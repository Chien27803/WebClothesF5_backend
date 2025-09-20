package com.javanc.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.TimeUnit;

@Configuration
@PropertySource("classpath:application-uat.yml")
public class CaffeineConfig {

    @Value("${jwt.expiry_time_token}")
    String EXPIRE_TIME;

    @Bean
    public com.github.benmanes.caffeine.cache.Cache<String, ?> caffeineCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(Long.parseLong(EXPIRE_TIME), TimeUnit.SECONDS)
                .maximumSize(1000)
                .recordStats()
                .build();
    }
}
