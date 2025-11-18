package com.javanc.controller.client;

import com.javanc.model.request.client.ChatRequest;
import com.javanc.model.response.ApiResponseDTO;
import com.javanc.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    @Autowired
    ChatService chatService;

    @PostMapping("/chatbot")
    public ResponseEntity<?> chatbot(@RequestBody ChatRequest chatRequest) {
        String result1 = chatService.chatbot(chatRequest);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<String>builder()
                        .message("ok")
                        .result(result1)
                        .build()
        );

    }
}
