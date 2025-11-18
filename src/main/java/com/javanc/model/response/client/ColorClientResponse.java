package com.javanc.model.response.client;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ColorClientResponse {
    Long id;
    String name;
    String hexCode;
}
