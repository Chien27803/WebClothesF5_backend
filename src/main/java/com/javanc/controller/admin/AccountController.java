package com.javanc.controller.admin;

import com.javanc.model.request.admin.AccountAdminRequest;
import com.javanc.model.response.ApiResponseDTO;
import com.javanc.model.response.admin.AccountPaginationResponse;
import com.javanc.service.AccountService;
import com.javanc.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/admin/accounts")
public class AccountController {

    AccountService accountService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ACCOUNT_ADD')")
    public ResponseEntity<?> createAccount(@Valid @ModelAttribute AccountAdminRequest accountAdminRequest, BindingResult result) throws IOException {
        if(accountAdminRequest.getEmail() == null){
            result.rejectValue("email", "email.required", "Email không để trống");
        }
        if(accountAdminRequest.getPassword() == null){
            result.rejectValue("password", "password.required", "Password không để trống");
        }
        if(accountAdminRequest.getUsername() == null){
            result.rejectValue("username", "username.required", "Username không để trống");
        }
        if(accountAdminRequest.getAvatar() == null){
            result.rejectValue("avatar", "avatar.required", "Ảnh đại diện không để trống");
        }
        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors() // lấy các field lỗi
                    .stream().map(FieldError::getDefaultMessage) // lấy message của từng field bị lỗi
                    .collect(Collectors.toList());
            ApiResponseDTO<List<String>> apiResponseDTO = ApiResponseDTO.<List<String>>builder()
                    .code(400)
                    .result(errorMessages)
                    .build();
            return ResponseEntity.badRequest().body(apiResponseDTO);
        }
        accountService.create(accountAdminRequest);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("Tạo tài khoản thành công")
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ACCOUNT_DELETE')")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("Xoá thành công")
                        .build()
        );
    }
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ACCOUNT_UPDATE')")
    public ResponseEntity<?> updateAccount (@PathVariable Long id, @RequestBody AccountAdminRequest accountAdminRequest, BindingResult result) throws IOException {
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(ApiResponseDTO.<List<String>>builder().code(400).result(errors).build());
        }
        accountService.update(id, accountAdminRequest);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder().message("Cập nhập thành công").build()
        );
    }



    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CATEGORY_MANAGEMENT', 'ROLE_PRODUCT_MANAGEMENT', 'ROLE_ACCOUNT_MANAGEMENT', 'ROLE_ROLE_MANAGEMENT')")
    public ResponseEntity<?> filterAccount(@RequestParam(required = true) String searchKey,
                                           @RequestParam(required = true) Long pageNumber,
                                           @RequestParam(required = true) String status,
                                           @RequestParam(required = true) String roleId) {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<AccountPaginationResponse>builder()
                        .result(accountService.getAccountList(searchKey, status, pageNumber, roleId))
                        .build());
    }
}
