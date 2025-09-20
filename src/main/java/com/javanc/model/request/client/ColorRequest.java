package com.javanc.model.request.client;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ColorRequest {
    @NotNull(message = "Color id is mandatory")
    Long id;
    @NotBlank(message = "Color name is mandatory")
    String name;
    @NotBlank(message = "Color hex is mandatory")
    String hex;
}
