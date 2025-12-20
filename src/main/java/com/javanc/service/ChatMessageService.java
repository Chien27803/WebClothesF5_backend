package com.javanc.service;

import com.javanc.model.request.client.ChatMessageRequest;
import com.javanc.model.response.client.ChatMessageResponse;

import java.util.List;

public interface ChatMessageService {
    ChatMessageResponse createChatMessage(Long chatRoomId, ChatMessageRequest chatMessageRequest);
    <T> List<ChatMessageResponse> getChatMessages(T roomId, T userId);
}
