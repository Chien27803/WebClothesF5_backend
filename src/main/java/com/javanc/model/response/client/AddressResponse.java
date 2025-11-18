package com.javanc.model.response.client;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressResponse {
    Long userAddressId;
    Long addressId;
    Long cityId;
    Long wardId;
    Long districtId;
    String detail;
    Long userId;
    Boolean isDefault;
}
