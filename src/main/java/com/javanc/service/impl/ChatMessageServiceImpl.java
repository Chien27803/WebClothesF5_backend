package com.javanc.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javanc.model.request.client.ChatMessageRequest;
import com.javanc.model.response.client.ChatMessageResponse;
import com.javanc.service.ChatMessageService;
import com.javanc.ultis.DateToLocalDateTimeUltis;
import com.javanc.ultis.GetTimeAgo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    RedisTemplate<String, Object> redisTemplate;
    ObjectMapper objectMapper;

    @Override
    public ChatMessageResponse createChatMessage(Long chatRoomId, ChatMessageRequest chatMessageRequest) {
        String key = String.format("chat_room:%s:user:%s:message", chatRoomId, chatMessageRequest.getReceiverId());
        Map<String, Object> msg = new HashMap<>();

        msg.put("senderId", chatMessageRequest.getSenderId());
        msg.put("message", chatMessageRequest.getMessage());
        msg.put("createdAt", new Date());
        redisTemplate.opsForList().rightPush(key, msg);
        if (redisTemplate.getExpire(key) == -1) {
            redisTemplate.expire(key, 10, TimeUnit.MINUTES);
        }

        return ChatMessageResponse.builder().message(chatMessageRequest.getMessage())
                .senderId(chatMessageRequest.getSenderId())
                .createdAt(GetTimeAgo.getTimeAgo(DateToLocalDateTimeUltis.getDateToLocalDateTime(chatMessageRequest.getCreatedAt())))
                .build();
    }

    @Override
    public <T> List<ChatMessageResponse> getChatMessages(T roomId, T userId) {
        String key = String.format("chat_room:%s:user:%s:message", roomId, userId);
        List<Object> messages;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            messages = redisTemplate.opsForList().range(key, 0, -1);
        } else {
            Map<String, Object> initMessage = new HashMap<>();
            initMessage.put("senderId", 2);
            initMessage.put("message", "Xin chào tôi là ADMIN");
            initMessage.put("createdAt", new Date());
            messages = Arrays.asList(initMessage);
            redisTemplate.opsForList().rightPush(key, initMessage);
            redisTemplate.expire(key, 10, TimeUnit.MINUTES);
        }
        List<ChatMessageResponse> res = new ArrayList<>();
        for(Object item : messages) {
            ChatMessageRequest chatMessageRequest = objectMapper.convertValue(item, ChatMessageRequest.class);
            ChatMessageResponse chatMessageResponse = ChatMessageResponse.builder()
                    .senderId(chatMessageRequest.getSenderId())
                    .message(chatMessageRequest.getMessage())
                    .createdAt(GetTimeAgo.getTimeAgo(DateToLocalDateTimeUltis.getDateToLocalDateTime(chatMessageRequest.getCreatedAt())))
                    .build();
            res.add(chatMessageResponse);
        }
        return res;
    }
}
