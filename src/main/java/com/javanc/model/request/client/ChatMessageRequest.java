package com.javanc.model.request.client;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageRequest {
    Long senderId;
    Long receiverId;
    String message;
    Date createdAt;
}
