package com.javanc.controller.client;

import com.javanc.model.response.ApiResponseDTO;
import com.javanc.model.response.client.ProductClientResponse;
import com.javanc.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductClientController {

    ProductService productService;

    @GetMapping
    public ResponseEntity<?> getProducts(@RequestParam(required = false) Long categoryId, @RequestParam(required = false) String searchKey) {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<List<ProductClientResponse>>builder()
                        .result(productService.getProductsForClient(categoryId, searchKey))
                        .build()
        );
    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> getDetailProduct(@PathVariable String slug) {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<ProductClientResponse>builder()
                        .result(productService.getDetailProduct(slug))
                        .build()
        );
    }

}
