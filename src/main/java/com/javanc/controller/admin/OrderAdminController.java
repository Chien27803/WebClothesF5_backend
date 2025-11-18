package com.javanc.controller.admin;

import com.javanc.model.response.ApiResponseDTO;
import com.javanc.model.response.admin.OrderAdminResponse;
import com.javanc.service.OrderAdminService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/api/admin/ordersAdmin")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderAdminController {

    final OrderAdminService orderAdminService;

    @GetMapping
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ORDER_VIEW')")
    public ResponseEntity<?> getAllOrders() {
        List<OrderAdminResponse> orders = orderAdminService.getAllOrders();
        return ResponseEntity.ok(ApiResponseDTO.<List<OrderAdminResponse>>builder()
                .result(orders)
                .build());
    }

    @PatchMapping("/{id}/status")
// @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ORDER_UPDATE')")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        orderAdminService.updateStatus(id, status);
        return ResponseEntity.ok(ApiResponseDTO.<Void>builder()
                .message("Cập nhật trạng thái đơn hàng thành công")
                .build());
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ORDER_VIEW')")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        OrderAdminResponse order = orderAdminService.getOrderById(id);
        return ResponseEntity.ok(ApiResponseDTO.<OrderAdminResponse>builder()
                .result(order)
                .build());
    }
}
