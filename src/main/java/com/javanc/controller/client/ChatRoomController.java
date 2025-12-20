package com.javanc.controller.client;


import com.javanc.model.response.ApiResponseDTO;
import com.javanc.model.response.client.ChatMessageResponse;
import com.javanc.repository.entity.ChatRoomEntity;
import com.javanc.service.ChatMessageService;
import com.javanc.service.ChatRoomService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat-rooms")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class ChatRoomController {

    ChatRoomService chatRoomService;
    ChatMessageService chatMessageService;

    @PostMapping
    public ResponseEntity<?> createChatRoom () {
        ChatRoomEntity chatRoom = chatRoomService.createOrGetChatRoom();
        List<ChatMessageResponse> res = chatMessageService.getChatMessages(chatRoom.getId(), chatRoom.getUser().getId());
        Map<String, Object> data = new HashMap<>();
        data.put("chatRoomId", chatRoom.getId());
        data.put("messages", res);
        return ResponseEntity.ok().body(
                ApiResponseDTO.builder()
                        .result(data)
                        .build()
        );
    }
}
