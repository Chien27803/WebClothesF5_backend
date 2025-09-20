package com.javanc.model.response;


import com.javanc.model.response.client.AddressResponse;
import com.javanc.repository.entity.AddressEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileResponse {
    Long id;
    String name;
    String email;
    Boolean status;
    String avatar;
    String phone;
    List<AddressResponse> addresses;
}
