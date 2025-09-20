package com.javanc.model.response.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.javanc.repository.entity.CategoryEntity;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductAdminResponse {
    Long id;
    String name;
    String slug;
    float price;
    Long quantity;
    String description;
    Boolean deleted;
    CategoryEntity category;
    List<String> src;
    List<DetailResponse> variants;
}
