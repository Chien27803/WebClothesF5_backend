package com.javanc.model.response.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderAdminResponse {
    Long id;
    Date time;
    String status;
    Float total;
    String username;
    String address;
    String payment;
    Boolean deleted;
    List<ProductAdminResponse> products;
}
