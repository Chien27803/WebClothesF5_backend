package com.javanc.model.request.client;

import com.javanc.repository.entity.FeedbackEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.BindParam;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackRequest {
    Long productId;
    float rate;
    String description;
}
