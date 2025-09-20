package com.javanc.config;

import com.javanc.controlleradvice.customeException.AppException;
import com.javanc.enums.ErrorCode;
import com.javanc.repository.RoleRepository;
import com.javanc.repository.UserRepository;
import com.javanc.repository.entity.RoleEntity;
import com.javanc.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ApplicationInitConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner init(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {

            if (userRepository.findByEmail("admin@admin.com").isEmpty()) {
                RoleEntity roleEntity = roleRepository.findByCode("ADMIN").orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
                UserEntity userEntity = UserEntity.builder()
                        .email("admin@admin.com")
                        .password(passwordEncoder.encode("12345"))
                        .roles(List.of(roleEntity))
                        .build();
                userRepository.save(userEntity);
                log.info("admin user created with by default password 12345");
            }
        };
    }



    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Thêm interceptor để tự động thêm header Authorization cho Qdrant
        restTemplate.getInterceptors().add(new ClientHttpRequestInterceptor() {
            @Override
            public org.springframework.http.client.ClientHttpResponse intercept(
                    HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                request.getHeaders()
                        .add(
                                "api-key",
                                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3MiOiJtIn0.6XzWcVRx9BeNkiySMqLUnLhPRsaJE5gzcO4CJckCu_4");
                return execution.execute(request, body);
            }
        });

        return restTemplate;
    }

}
