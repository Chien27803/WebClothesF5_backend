package com.javanc.controller.admin;


import com.javanc.model.request.admin.PermissionAdminRequest;
import com.javanc.model.response.ApiResponseDTO;
import com.javanc.model.response.PermissionResponse;
import com.javanc.repository.PermissionRepository;
import com.javanc.repository.entity.PermissionEntity;
import com.javanc.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Permission;
import java.util.List;

@RestController
@RequestMapping("/api/admin/permissions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor

public class PermissionController {

    PermissionService permissionService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CATEGORY_MANAGEMENT', 'ROLE_PRODUCT_MANAGEMENT', 'ROLE_ACCOUNT_MANAGEMENT', 'ROLE_ROLE_MANAGEMENT', 'ROLE_ORDER_MANAGEMENT')")
    public ResponseEntity<?> getPermissions() {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<List<PermissionResponse>>builder()
                        .result(permissionService.listPermissions())
                        .build()
        );
    }

    @PatchMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ROLE_PERMISSION')")
    public ResponseEntity<?> updatePermissions(@RequestBody List<PermissionAdminRequest> listPermissionAdminRequest) {
        permissionService.updatePermissions(listPermissionAdminRequest);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("Ok")
                        .build()
        );
    }
}
