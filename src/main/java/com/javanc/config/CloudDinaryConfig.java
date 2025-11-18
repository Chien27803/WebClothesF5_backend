package com.javanc.config;


import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration

@PropertySource("classpath:application.yml")
public class CloudDinaryConfig {

    @Value("${cloud.api_key}")
    private String API_KEY;
    @Value("${cloud.api_name}")
    private String API_NAME;
    @Value("${cloud.api_secret}")
    private String API_SECRET;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("api_key", API_KEY);
        config.put("cloud_name", API_NAME);
        config.put("api_secret", API_SECRET);
        return new Cloudinary(config);
    }
}
