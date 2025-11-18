package com.javanc.config;

import com.javanc.enums.CityEnum;
import com.javanc.model.response.ApiResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/cities")
public class CityController {

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Map<String, String>>builder()
                        .result(CityEnum.getCity())
                        .build()
        );
    }
}
