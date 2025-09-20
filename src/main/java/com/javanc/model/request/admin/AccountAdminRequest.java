package com.javanc.model.request.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountAdminRequest {
    Long id;
    @NotBlank(message= "Role_id không để trống")
    String role_id;
    String username;
    String password;
    String email;
    MultipartFile avatar;
}
