package com.javanc.model.request.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleAdminRequest {
    Long id;
    @NotBlank(message = "Tên quyền không để trống")
    String name;
    @NotBlank(message = "Mã quyền không để trống")
    String code;
    @NotBlank(message = "Mô tả không để trống")
    String description;
    Boolean deleted;
}
