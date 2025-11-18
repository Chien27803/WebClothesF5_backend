package com.javanc.model.request.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressRequest {
    Long addressId;
    @NotNull(message = "City cannot be blank")
    Long cityId;
    @NotNull(message = "Ward cannot be blank")
    Long wardId;
    @NotNull(message = "District cannot be blank")
    Long districtId;
    @NotBlank(message = "Detail cannot be blank")
    String detail;
    @NotNull(message = "Default address cannot be blank")
    Boolean isDefault;
    Long userAddressId;
}
