package com.javanc.controller.admin;

import com.javanc.service.ChatbotTrainningService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/chatbot/trainning")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ChatbotTrainningController {

    ChatbotTrainningService chatbotTrainningService;

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> retrainProducts() {
        try {
            chatbotTrainningService.trainningWithProduct();
//            chatbotTrainningService.trainningWithFAQ();
            return ResponseEntity.ok("Training completed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Training failed: " + e.getMessage());
        }
    }
}
