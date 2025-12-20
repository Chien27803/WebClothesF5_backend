package com.javanc.service;

import com.javanc.model.request.client.ChatRequest;
import com.javanc.model.response.client.ChatbotResponse;

import java.util.Map;

public interface ChatbotService {
    ChatbotResponse answer(Map<String, Object> question);
}
