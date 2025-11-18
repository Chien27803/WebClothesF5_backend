package com.javanc.controller.client;


import com.javanc.model.request.client.OrderRequest;
import com.javanc.model.request.client.ProductCartItemRequest;
import com.javanc.model.response.ApiResponseDTO;
import com.javanc.model.response.client.OrderResponse;
import com.javanc.service.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderController {

    OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest orderRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors() // lấy các field lỗi
                    .stream().map(FieldError::getDefaultMessage) // lấy message của từng field bị lỗi
                    .collect(Collectors.toList());
            ApiResponseDTO<List<String>> apiResponseDTO = ApiResponseDTO.<List<String>>builder()
                    .code(400)
                    .result(errorMessages)
                    .build();
            return ResponseEntity.badRequest().body(apiResponseDTO);
        }
        orderService.create(orderRequest);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<String>builder()
                        .message("Order created successfully")
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<?> getOrder() {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<List<OrderResponse>>builder()
                        .result(orderService.getAll())
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id){
        orderService.delete(id);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("Order deleted successfully")
                        .build()
        );
    }
}
