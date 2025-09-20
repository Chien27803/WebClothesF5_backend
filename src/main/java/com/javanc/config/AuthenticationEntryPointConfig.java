package com.javanc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javanc.enums.ErrorCode;
import com.javanc.model.response.ApiResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class AuthenticationEntryPointConfig implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // lay ma loi
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        // http code trả về khi truy cập mà chưa authentication token
        response.setStatus(errorCode.getHttpStatus().value());

        // response là JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ApiResponseDTO<?> apiResponseDTO = ApiResponseDTO.builder()
                .code(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage())
                .build();
        // chuyển Java object -> JSON
        ObjectMapper objectMapper = new ObjectMapper();

//        Ghi JSON vào phản hồi HTTP
        response.getWriter().write(objectMapper.writeValueAsString(apiResponseDTO));

//        Gửi phản hồi ngay lập tức bằng flush()
        response.getWriter().flush();
    }
}
