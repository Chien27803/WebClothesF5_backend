package com.javanc.controller.admin;

import com.javanc.model.request.admin.DiscountAdminRequest;
import com.javanc.model.response.ApiResponseDTO;
import com.javanc.model.response.admin.DiscountAdminResponse;
import com.javanc.service.ProductDiscountService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.validation.BindingResult;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/discounts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductDiscountController {

    ProductDiscountService productDiscountService;

    @GetMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_PRODUCT_MANAGEMENT')")
    public ResponseEntity<?> findAll(@PathVariable Long productId) {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<List<DiscountAdminResponse>>builder()
                        .result(productDiscountService.findAllByProductId(productId))
                        .build()
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_PRODUCT_MANAGEMENT')")
    public ResponseEntity<?> create(@Valid @RequestBody DiscountAdminRequest request, BindingResult result) {
        if (request.getStart_date() == null || request.getEnd_date() == null ||
                request.getEnd_date().before(request.getStart_date())) {
            result.rejectValue("end_date", "error.end_date", "Ngày kết thúc phải sau hoặc bằng ngày bắt đầu");
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
        productDiscountService.createProductDisconut(request);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("Product discount created successfully")
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_PRODUCT_MANAGEMENT')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productDiscountService.deleteProductDiscountByDiscountId(id);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("Product discount deleted successfully")
                        .build()
        );
    }
}
