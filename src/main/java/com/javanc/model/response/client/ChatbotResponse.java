package com.javanc.model.response.client;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatbotResponse {
    String answer;
    Boolean needAdminSupport;
}
