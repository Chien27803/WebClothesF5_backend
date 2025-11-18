package com.javanc.model.request.auth;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterRequest extends LoginRegister{

    @Pattern(regexp = "^0[0-9]{9}$", message = "Số điện thoại không đúng định dạng!")
    String phone;
    @NotNull(message = "Vui lòng cung cáp địa chỉ")
    Long cityId;
    @NotNull(message = "Vui lòng cung cáp địa chỉ")
    Long districtId;
    @NotNull(message = "Vui lòng cung cáp địa chỉ")
    Long wardId;
    @NotBlank(message = "Vui lòng cung cáp địa chỉ")
    String detail;
    @NotBlank(message = "Vui lòng cung cấp họ tên")
    String name;
    String src;
    @JsonIgnore
    MultipartFile avatar;
}
