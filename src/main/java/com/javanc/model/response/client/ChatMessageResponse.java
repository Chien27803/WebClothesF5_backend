package com.javanc.model.response.client;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponse {
    Long senderId;
    String message;
    String createdAt;
}
