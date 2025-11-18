package com.javanc.controller.admin;

import com.javanc.model.request.admin.ProductAdminRequest;
import com.javanc.model.response.ApiResponseDTO;
import com.javanc.model.response.admin.ProductAdminResponse;
import com.javanc.model.response.admin.ProductPaginationResponse;
import com.javanc.service.ProductService;
import com.javanc.validation.VariantValidator;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/products")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductAdminController {

    ProductService productService;
    VariantValidator variantValidator;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'PRODUCT_ADD')")
    public ResponseEntity<?> createProduct(@Valid @ModelAttribute ProductAdminRequest productAdminRequest, BindingResult result) throws IOException {
        if (productAdminRequest.getImages() == null || productAdminRequest.getImages().isEmpty()) {
            result.rejectValue("images", "images.empty", "Phải chọn ít nhất một ảnh");
        }
        try {
            variantValidator.validateVariants(productAdminRequest.getVariants());
        } catch (Exception e) {
            result.rejectValue("variants", "variants.invalid", "Dữ liệu variants không hợp lệ: ");
        }
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

        productService.createProduct(productAdminRequest);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("OK")
                        .build()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CATEGORY_MANAGEMENT', 'ROLE_PRODUCT_MANAGEMENT', 'ROLE_ACCOUNT_MANAGEMENT', 'ROLE_ROLE_MANAGEMENT', 'ROLE_ORDER_MANAGEMENT')")
    public ResponseEntity<?> filterProduct(@RequestParam(required = true) String searchKey, @RequestParam(required = true) String categoryId, @RequestParam(required = true) String status, @RequestParam(required = true) String pageNumber) {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<ProductPaginationResponse>builder()
                        .result(productService.getProducts(searchKey, categoryId, status, pageNumber))
                        .build()
        );
    }

    @DeleteMapping("/{ids}")
    // api/products/1,2,3
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'PRODUCT_DELETE')")
    public ResponseEntity<?> deleteProduct(@PathVariable List<Long> ids) {
        productService.deleteProducts(ids);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("Xóa sản phẩm thành công")
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CATEGORY_MANAGEMENT', 'ROLE_PRODUCT_MANAGEMENT', 'ROLE_ACCOUNT_MANAGEMENT', 'ROLE_ROLE_MANAGEMENT', 'ROLE_ORDER_MANAGEMENT')")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<ProductAdminResponse>builder()
                        .result(productService.getProductById(id))
                        .build()
        );
    }

    @PatchMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'PRODUCT_UPDATE')")
    public ResponseEntity<?> updateProduct(@Valid @ModelAttribute ProductAdminRequest productAdminRequest, BindingResult result) throws IOException {
        productService.createProduct(productAdminRequest);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("OK")
                        .build()
        );
    }
}
