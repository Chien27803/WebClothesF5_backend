package com.javanc.model.response.client;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class DetailClientResponse {
    SizeClientResponse size;
    ColorClientResponse color;
    Long stock;
    Long soldCount;
}
