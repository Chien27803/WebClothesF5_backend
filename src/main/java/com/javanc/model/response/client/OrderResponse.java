package com.javanc.model.response.client;

import com.javanc.model.request.client.AddressRequest;
import com.javanc.model.request.client.ProductCartItemRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long id;
    String status;
    float total;
    Date time;
    AddressResponse address;
    PaymentResponse payment;
    List<ProductCartItemRequest> productCartItems;
}
