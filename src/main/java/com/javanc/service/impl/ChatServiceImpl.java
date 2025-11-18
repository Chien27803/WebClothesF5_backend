package com.javanc.service.impl;

import com.javanc.model.request.client.ChatRequest;
import com.javanc.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;

    public ChatServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public String chatbot(ChatRequest chatRequest) {
        return chatClient
                .prompt()
                .user(chatRequest.message())  // ✅ ĐÚNG CHUẨN
                .call()
                .content();
    }
}
