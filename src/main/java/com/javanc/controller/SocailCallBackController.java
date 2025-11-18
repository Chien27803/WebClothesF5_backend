package com.javanc.controller;


import com.javanc.model.response.ApiResponseDTO;
import com.javanc.model.response.AuthenResponse;
import com.javanc.service.AuthenService;
import com.javanc.service.OAuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("auth/login")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor

public class SocailCallBackController {

    OAuthService oAuthService;
    AuthenService authenService;

    @GetMapping("/{type}")
    public ResponseEntity<?> googleCallBack(@RequestParam("code") String code, @PathVariable("type") String type) throws IOException {
        Map<String, Object> info = oAuthService.fetchProfile(code, type);
        AuthenResponse response = authenService.loginWithGoogleOrFacebook(info);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<AuthenResponse>builder()
                        .result(response)
                        .build()
        );
    }
}
