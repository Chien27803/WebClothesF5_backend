package com.javanc.controller.admin;

import com.javanc.model.request.admin.CategoryAdminRequest;
import com.javanc.model.response.ApiResponseDTO;
import com.javanc.model.response.CategoryResponse;
import com.javanc.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryAdminController {

    CategoryService categoryService;


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'CATEGORY_ADD')")
    public ResponseEntity<?> createCategory(@RequestBody CategoryAdminRequest categoryAdminRequest) {
        categoryService.create(categoryAdminRequest);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("Tạo danh mục thành công")
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CATEGORY_MANAGEMENT', 'ROLE_PRODUCT_MANAGEMENT', 'ROLE_ACCOUNT_MANAGEMENT', 'ROLE_ROLE_MANAGEMENT', 'ROLE_USER', 'ROLE_ORDER_MANAGEMENT')")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<CategoryResponse>builder()
                        .result(categoryService.getById(id))
                        .build()
        );
    }
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'CATEGORY_UPDATE')")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryAdminRequest categoryAdminRequest) {
        categoryService.update(id, categoryAdminRequest);
        return ResponseEntity.ok().body(ApiResponseDTO.<Void>builder().message("Ok").build());
    }
}
