package com.javanc.controller.admin;


import com.javanc.model.response.ApiResponseDTO;
import com.javanc.model.response.client.ChatMessageResponse;
import com.javanc.service.ChatMessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/chat-messages")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ChatMessageAdminController {
    ChatMessageService chatMessageService;

    @PostMapping
    public ResponseEntity<?> getMessages(@RequestBody Map<String, Object> inforRoom) {
        String roomId = inforRoom.get("roomId").toString();
        String userId = inforRoom.get("userId").toString();
        return ResponseEntity.ok().body(
                ApiResponseDTO.<List<ChatMessageResponse>>builder()
                        .result(chatMessageService.getChatMessages(roomId, userId))
                        .build()
        );
    }
}
