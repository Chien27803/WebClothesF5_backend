package com.javanc.model.request.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import com.javanc.model.response.client.FeedbackResponse;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCartItemRequest {
    @NotNull(message = "Product id is mandatory")
    Long id;
    @NotNull(message = "Quantity is mandatory")
    Long quantity;
    Double percent;
    @NotNull(message = "Product name is mandatory")
    String name;
    float price;
    Long stock;
    ColorRequest color;
    SizeRequest size;
    ImageRequest image;
    List<FeedbackResponse> feedbacks;
}
