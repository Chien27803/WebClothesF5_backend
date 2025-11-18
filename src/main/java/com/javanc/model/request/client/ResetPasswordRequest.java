package com.javanc.model.request.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*\\W)(?=.*[A-Z]).{8,}$",
            message = "Mật khẩu phải có ít nhất 8 ký tự, bao gồm ít nhất 1 chữ hoa, 1 chữ số và 1 ký tự đặc biệt"
    )
    private String newPassword;
}
