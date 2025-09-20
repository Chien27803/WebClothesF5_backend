package com.javanc.controller.client;

import com.javanc.model.request.client.FeedbackRequest;
import com.javanc.model.response.ApiResponseDTO;
import com.javanc.service.FeedbackService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedbacks")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FeedbackController {

    FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<?> createFeedback(@RequestBody FeedbackRequest feedbackRequest) {
        feedbackService.create(feedbackRequest);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("Feedback created successfully")
                        .build()
        );
    }
}
