package com.javanc.model.response.client;

import com.javanc.repository.entity.ProductFavoriteEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FavoriteResponse {
    Long id;
    String email;
    List<ProductFavoriteEntity> productFavoriteEntities;
}
