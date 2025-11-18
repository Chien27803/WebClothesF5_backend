package com.javanc.controller.client;

import com.cloudinary.Api;
import com.javanc.model.request.client.ProductCartItemRequest;
import com.javanc.model.response.ApiResponseDTO;
import com.javanc.model.response.client.ProductFavoriteResponse;
import com.javanc.repository.ProductFavoriteRepository;
import com.javanc.service.ShoppingCartService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shopping-cart")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ShoppingCartController {

    ShoppingCartService shoppingCartService;

    ProductFavoriteRepository productFavoriteRepository;

    @PostMapping
    public ResponseEntity<?> addOrUpdateCart(@Valid @RequestBody List<ProductCartItemRequest> productCartItemRequests, BindingResult result) {
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
        shoppingCartService.createCart(productCartItemRequests);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("Add cart item success")
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<?> getCart() {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<List<ProductCartItemRequest>>builder()
                        .result(shoppingCartService.getCartByUser())
                        .build()
        );
    }

    @PatchMapping
    public ResponseEntity<?> updateCart(@Valid @RequestBody List<ProductCartItemRequest> productCartItemRequests, BindingResult result) {
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
        shoppingCartService.updateCart(productCartItemRequests);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("Update cart item success")
                        .build()
        );
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCart() {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("Delete cart item success")
                        .build()
        );
    }
}
