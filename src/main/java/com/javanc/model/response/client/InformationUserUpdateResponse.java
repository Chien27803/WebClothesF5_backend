package com.javanc.model.response.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InformationUserUpdateResponse {
    private String avatar;
    private String name;
    private String phone;
    private String email;
    List<AddressUpdateDTO> addressUpdateDTOs;
    @Data
    @Builder
    public static class AddressUpdateDTO{
        Long addressId;
        Long cityId;
        Long wardId;
        Long districtId;
        String detail;
        Boolean isDefault;
        Long userAddressId;
    }

}
