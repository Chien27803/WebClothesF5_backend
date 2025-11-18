package com.javanc.model.response.client;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ProductClientResponse {
    Long id;
    String name;
    String description;
    String slug;
    float price;
    Long quantity;
    CategoryClientResponse category;
    List<ImageClientResponse> images;
    List<DetailClientResponse> details;
    List<DiscountClientResponse> discounts;
    List<FeedbackResponse> feedbacks;
}
