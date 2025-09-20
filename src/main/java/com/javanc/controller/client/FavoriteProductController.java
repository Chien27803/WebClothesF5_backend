package com.javanc.controller.client;

import com.javanc.controlleradvice.customeException.AppException;
import com.javanc.enums.ErrorCode;
import com.javanc.model.request.client.ProductCartItemRequest;
import com.javanc.model.request.client.ProductFavoriteRequest;
import com.javanc.model.response.ApiResponseDTO;
import com.javanc.model.response.client.ProductFavoriteResponse;
import com.javanc.repository.FavoriteRepository;
import com.javanc.repository.UserRepository;
import com.javanc.repository.entity.UserEntity;
import com.javanc.service.impl.FavoriteProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/favorite")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor

public class FavoriteProductController {

     FavoriteProductService favoriteProductService;
     UserRepository userRepository;
     FavoriteRepository favoriteRepository;
     @GetMapping("/exists")
     public ResponseEntity<Boolean> checkFavoriteExists(){
         SecurityContext context = SecurityContextHolder.getContext();
         Authentication authentication = context.getAuthentication();
         String email = authentication.getName();
         UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
         boolean exists = favoriteRepository.findByUser(user).isPresent();
         return ResponseEntity.ok(exists);


     }

    @PostMapping
    public ResponseEntity<?> addFavoriteProduct(@Valid @RequestBody List<ProductFavoriteRequest> productFavoriteRequest, BindingResult result){
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors() // lấy các field lỗi
                    .stream().map(FieldError::getDefaultMessage) // lấy message của từng field bị lỗi
                    .collect(Collectors.toList());
            ApiResponseDTO<List<String>> apiResponseDTO = ApiResponseDTO.<List<String>>builder()
                    .code(400)
                    .result(errorMessages)
                    .build();
            return ResponseEntity.badRequest().body(apiResponseDTO);
        }else {
            favoriteProductService.createFavorite(productFavoriteRequest);
            return ResponseEntity.ok().body(
                    ApiResponseDTO.<Void>builder()
                            .message("Add favorite item success")

                            .build()
            );
        }


    }
    @GetMapping
    public ResponseEntity<?> getFavorite() {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<List<ProductFavoriteResponse>>builder()
                        .message("Get favorite items success")
                        .result(favoriteProductService.getFavoriteByUser())
                        .build()
        );
    }
    @PatchMapping
    public ResponseEntity<?> updateFavorite(@Valid @RequestBody List<ProductFavoriteRequest> productFavoriteRequests, BindingResult result) {
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
        favoriteProductService.updateFavorite(productFavoriteRequests);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("Update favorite item success")
                        .build()
        );
    }
    @DeleteMapping
    public ResponseEntity<?> deleteFavorite() {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("Delete favorite item success")
                        .build()
        );
    }
}
