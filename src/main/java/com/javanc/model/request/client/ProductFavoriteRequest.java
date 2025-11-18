package com.javanc.model.request.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestBody;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductFavoriteRequest {
    @NotNull(message = "Product id is mandatory")
    Long id;
    @NotNull(message = "Quantity is mandatory")
    Long quantity=1L;
    Double percent;
    @NotNull(message = "Product name is mandatory")
    String name;
    float price;
    Long stock;
    ColorRequest color;
    SizeRequest size;
    ImageRequest image;
}
