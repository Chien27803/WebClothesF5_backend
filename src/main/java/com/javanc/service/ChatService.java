package com.javanc.service;

import com.javanc.model.request.client.ChatRequest;

public interface ChatService {
    String chatbot(ChatRequest chatRequest);
}
