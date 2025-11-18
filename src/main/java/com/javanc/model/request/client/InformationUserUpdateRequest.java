package com.javanc.model.request.client;
import com.javanc.repository.entity.UserAddressEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InformationUserUpdateRequest {

    String avatar;
    String name;
    String email;
    String phone;
   List <AddressRequest> addressRequestList;
}
