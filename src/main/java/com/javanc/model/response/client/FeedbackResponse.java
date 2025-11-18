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
public class FeedbackResponse {
    Long id;
    Long userId;
    String name;
    String description;
    Date time;
    float rating;
}
