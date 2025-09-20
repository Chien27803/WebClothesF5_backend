package com.javanc.model.response.admin;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AccountAdminResponse {
    Long id;
    String name;
    String email;
    String avatar;
    LocalDateTime created;
    boolean deleted;
    boolean status;
    RoleResponse role;
}
