package com.javanc.model.response.client;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class DiscountClientResponse {
    Long id;
    Double percent;
    Date startDate;
    Date endDate;
}