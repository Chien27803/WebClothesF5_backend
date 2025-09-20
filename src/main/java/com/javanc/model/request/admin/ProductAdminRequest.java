package com.javanc.model.request.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductAdminRequest {
    Long id;
    @NotNull(message = "Thư mục không để trống!")
    Long categoryId;
    @NotBlank(message = "Tên không để trống")
    String name;
    @NotBlank(message = "Slug không để trống")
    String slug;
    @Size(min = 1, message = "Vui lòng chọn ít nhất một ảnh sản phẩm!")
    List<MultipartFile> images;
    @NotBlank(message = "Mô tả không để trống")
    String description;
    @NotNull(message = "Giá không để trống")
    Float price;
    @NotNull(message = "Số lượng không để trống")
    Long quantity;
    String variants;
}
