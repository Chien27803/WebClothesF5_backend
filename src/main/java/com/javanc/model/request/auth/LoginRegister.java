package com.javanc.model.request.auth;


import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class LoginRegister {
    @Pattern(regexp = "^[A-Za-z0-9]+@[A-Za-z0-9]+\\.(com|vn)$", message = "Email không đúng định dạng!")
    String email;
    @Pattern(regexp = "(?=.*\\d)(?=.*\\W)(?=.*[A-Z]).{8,}", message = "Mật khẩu tối thiểu 8 ký tự (ít nhất 1 chữ hoa, 1 số và 1 ký tự đặc biệt)!")
    String password;
}
