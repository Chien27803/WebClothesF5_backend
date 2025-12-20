package com.javanc.controller.client;

import com.javanc.model.response.ApiResponseDTO;
import com.javanc.model.response.client.ChatbotResponse;
import com.javanc.service.ChatbotService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ChatbotController {

    ChatbotService chatbotService;

    @PostMapping("/ask")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> ask(@RequestBody Map<String,Object> request) {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<ChatbotResponse>builder()
                        .result(chatbotService.answer(request))
                        .build()
        );
    }
}
