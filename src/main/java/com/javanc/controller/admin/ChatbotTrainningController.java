package com.javanc.controller.admin;


import com.javanc.service.ChatbotTrainningService;
import com.javanc.service.QdrantService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/chatbot/trainning")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ChatbotTrainningController {

    ChatbotTrainningService chatbotTrainningService;
    QdrantService qdrantService;

    @PostMapping
    public ResponseEntity<String> retrainProducts() {
        try {
            chatbotTrainningService.trainningWithProduct();
//            chatbotTrainningService.trainningWithFAQ();
            return ResponseEntity.ok("Training completed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Training failed: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> createCollection() {
        try {
            qdrantService.createCollection();
            return ResponseEntity.ok("Collection completed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Create collection failed: " + e.getMessage());
        }
    }

}

