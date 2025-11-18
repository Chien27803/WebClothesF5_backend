package com.javanc.controller.client;

import com.cloudinary.api.ApiResponse;
import com.javanc.model.request.client.PaymentRequest;
import com.javanc.model.response.ApiResponseDTO;
import com.javanc.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PaymentController {

    PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest paymentRequest, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<String>builder()
                        .result(paymentService.createPayment(paymentRequest, httpServletRequest))
                        .build()
        );
    }
}
