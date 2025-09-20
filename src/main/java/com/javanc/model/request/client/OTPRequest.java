package com.javanc.model.request.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OTPRequest {
    @NotBlank
    @Email
    private String email;
}
