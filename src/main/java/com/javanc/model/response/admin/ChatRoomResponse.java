package com.javanc.model.response.admin;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatRoomResponse {
    Long chatRoomId;
    Long userId;
    String name;
    String avatar;
}
