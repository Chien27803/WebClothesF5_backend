package com.javanc.controller.client;
import com.javanc.model.response.ApiResponseDTO;
import com.javanc.service.ChatbotService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ChatbotController {

    ChatbotService chatbotService;

    @PostMapping("/ask")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> ask(@RequestBody Map<String,Object> request) {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<String>builder()
                        .result(chatbotService.answer(request))
                        .build()
        );
    }
}
