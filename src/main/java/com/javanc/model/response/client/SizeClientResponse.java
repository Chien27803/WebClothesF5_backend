package com.javanc.model.response.client;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class SizeClientResponse {
    Long id;
    String name;
    String description;
}
