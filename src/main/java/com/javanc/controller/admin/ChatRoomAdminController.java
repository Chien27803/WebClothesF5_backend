package com.javanc.controller.admin;


import com.javanc.model.response.ApiResponseDTO;
import com.javanc.model.response.admin.ChatRoomResponse;
import com.javanc.service.ChatRoomService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/chat-rooms")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ChatRoomAdminController {

    ChatRoomService chatRoomService;

    @GetMapping
    public ResponseEntity<?> getChatRooms() {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<List<ChatRoomResponse>>builder()
                        .result(chatRoomService.getChatRooms())
                        .build()
        );
    }

}
