package com.javanc.model.request.admin;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class PermissionAdminRequest {
    Long roleId;
    List<String> permissions;
}
