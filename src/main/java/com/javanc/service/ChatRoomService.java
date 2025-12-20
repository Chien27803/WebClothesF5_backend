package com.javanc.service;

import com.javanc.model.response.admin.ChatRoomResponse;
import com.javanc.repository.entity.ChatRoomEntity;

import java.util.List;

public interface ChatRoomService {
    ChatRoomEntity createOrGetChatRoom();
    List<ChatRoomResponse> getChatRooms();
}
