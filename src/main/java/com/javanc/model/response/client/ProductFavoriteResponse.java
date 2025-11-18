package com.javanc.model.response.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.javanc.model.request.client.ColorRequest;
import com.javanc.model.request.client.ImageRequest;
import com.javanc.model.request.client.SizeRequest;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductFavoriteResponse {
    @NotNull(message = "Product id is mandatory")
    Long id;
    @NotNull(message = "Quantity is mandatory")
    Long quantity=1L;
    Double percent;
    @NotNull(message = "Product name is mandatory")
    String name;
    float price;
    String slug;
    Long stock;
    ColorRequest color;
    SizeRequest size;
    ImageRequest image;
}
