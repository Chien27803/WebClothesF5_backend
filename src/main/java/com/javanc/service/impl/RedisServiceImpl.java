package com.javanc.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javanc.model.request.auth.RegisterRequest;
import com.javanc.model.request.client.OTPRequest;
import com.javanc.service.RedisService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    RedisTemplate<String, Object> redisTemplate;
    ObjectMapper objectMapper;

    @Override
    public void saveOTP(String email, String otp) {
        String key = "otp:" + email;
        redisTemplate.opsForValue().set(key, otp, 5, TimeUnit.MINUTES);
    }


    @Override
    public String getOTP(String email) {
        String key = "otp:" + email;
        Object value = redisTemplate.opsForValue().get(key);
        return value == null ? null : value.toString();
    }

    @Override
    public void deleteOTP(String email) {
        String key = "otp:" + email;
        redisTemplate.delete(key);
    }


    @Override
    public void savePendingUserOTP(String email, String otp, OTPRequest otpRequest) {
        String key = "otp:" + email;
        redisTemplate.opsForValue().set(key, otp, 5, TimeUnit.MINUTES);
    }



    @Override
    public void savePendingUser(String email, String otp, RegisterRequest registerRequest) throws JsonProcessingException {
        String key = String.format("pendingUser:%s:%s", email, otp);
        String json = objectMapper.writeValueAsString(registerRequest);
        redisTemplate.opsForValue().set(key, json, 5, TimeUnit.MINUTES);
    }

        @Override
    public RegisterRequest getPendingUser(String email, String otp) throws JsonProcessingException {
        String key = String.format("pendingUser:%s:%s", email, otp);
        String json = (String) redisTemplate.opsForValue().get(key);
        return json != null ? objectMapper.readValue(json, new TypeReference<RegisterRequest>() {
            }) : null;
    }

}
