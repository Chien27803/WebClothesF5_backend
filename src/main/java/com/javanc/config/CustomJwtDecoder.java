package com.javanc.config;


import com.javanc.model.request.AuthenRequest;
import com.javanc.model.response.AuthenResponse;
import com.javanc.service.AuthenService;
import com.nimbusds.jose.JOSEException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Component
@Slf4j
@PropertySource("classpath:application.yml")
public class CustomJwtDecoder implements JwtDecoder {

    @Value("${jwt.sign_key}")
    private String signerKey;

    @Autowired
    private AuthenService authenService;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {

        try {
            AuthenResponse response = authenService.introspect(AuthenRequest.builder()
                    .token(token)
                    .build());
            if (!response.getValid()) {
                log.error("Token is invalid");
                throw new BadJwtException("Token invalid");
//                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
        } catch (JOSEException | ParseException e) {
            throw new JwtException(e.getMessage());
        }

        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder
                    .withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}