package com.javanc.model.request.common;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailCommonResquest {
    Long sizeId;
    Long colorId;
    Long stock;
}
