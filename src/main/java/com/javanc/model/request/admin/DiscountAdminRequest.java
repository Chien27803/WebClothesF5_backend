package com.javanc.model.request.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscountAdminRequest {
    @NotNull(message = "productId không được để trống")
    Long productId;
    @NotNull(message = "percent không được để trống")
    @DecimalMin(value = "1.0", inclusive = true, message = "percent phải >= 1")
    @DecimalMax(value = "100.0", inclusive = true, message = "percent phải <= 100")
    Double percent;
    Date start_date;
    Date end_date;
}
