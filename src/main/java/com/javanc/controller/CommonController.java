package com.javanc.controller;

import com.javanc.model.response.ApiResponseDTO;

import com.javanc.model.response.CategoryResponse;
import com.javanc.model.response.client.ColorClientResponse;
import com.javanc.model.response.common.SizeResponse;
import com.javanc.repository.entity.ColorEntity;
import com.javanc.repository.entity.SizeEntity;
import com.javanc.service.CategoryService;

import com.javanc.service.ColorService;
import com.javanc.service.ProductService;
import com.javanc.service.SizeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/common")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommonController {

    ColorService colorService;
    SizeService sizeService;
    CategoryService categoryService;

    @GetMapping("/colors")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getColors() {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<List<ColorClientResponse>>builder()
                        .result(colorService.getColors())
                        .build()
        );
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/sizes")
    public ResponseEntity<?> getSizes() {
        return ResponseEntity.ok().body(

                ApiResponseDTO.<List<SizeResponse>>builder()
                        .result(sizeService.getSizes())
                        .build()
        );
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<List<CategoryResponse>>builder()
                        .result(categoryService.getAll())
                        .build()
        );
    }
}
