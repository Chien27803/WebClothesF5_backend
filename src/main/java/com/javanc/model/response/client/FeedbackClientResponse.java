package com.javanc.model.response.client;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackClientResponse {
    Long id;
    Long userId;
    String description;
    Date time;
    float rating;
}
