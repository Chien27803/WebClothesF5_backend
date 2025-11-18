package com.javanc.controller.admin;

import com.javanc.model.request.admin.RoleAdminRequest;
import com.javanc.model.response.ApiResponseDTO;
import com.javanc.repository.entity.RoleEntity;
import com.javanc.service.RoleService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class RoleAdminController {

    RoleService roleService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CATEGORY_MANAGEMENT', 'ROLE_PRODUCT_MANAGEMENT', 'ROLE_ACCOUNT_MANAGEMENT', 'ROLE_ROLE_MANAGEMENT', 'ROLE_ORDER_MANAGEMENT')")
    public ResponseEntity<?> getRoles(){
//        Authentication securityContextHolder = SecurityContextHolder.getContext().getAuthentication();
//        log.info("User name: " + securityContextHolder.getName());
//        securityContextHolder.getAuthorities().forEach(grantedAuthority -> log.info("GrantedAuthority: {}", grantedAuthority));
        return ResponseEntity.ok().body(
                ApiResponseDTO.<List<RoleEntity>>builder()
                        .result(roleService.getAll())
                        .build()
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ADD')")
    public ResponseEntity<?> createRole(@Valid @RequestBody RoleAdminRequest roleAdminRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            return ResponseEntity.badRequest().body(
                    ApiResponseDTO.<List<String>>builder()
                            .code(400)
                            .result(errors)
                            .build()
            );
        }
        roleService.create(roleAdminRequest);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("Tạo quyền thành công")
                        .build()
        );
    }


    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_UPDATE')")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @Valid @RequestBody RoleAdminRequest roleAdminRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            return ResponseEntity.badRequest().body(
                    ApiResponseDTO.<List<String>>builder()
                            .code(400)
                            .result(errors)
                            .build()
            );
        }
        roleService.update(id, roleAdminRequest);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("Cập nhập thành công")
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_DELETE')")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("Xóa thành công")
                        .build()
        );
    }
}
