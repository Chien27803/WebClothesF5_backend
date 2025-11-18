package com.javanc.controller.client;


import com.javanc.controlleradvice.customeException.AppException;
import com.javanc.enums.ErrorCode;
import com.javanc.model.response.ApiResponseDTO;
import com.javanc.repository.UserRepository;
import com.javanc.repository.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/users")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserClientController {

    UserRepository userRepository;

    @GetMapping("/{id}")
    @PostAuthorize("returnObject.body.email == authentication.name")
    public ResponseEntity<?> getUser(@PathVariable long id) {
            return ResponseEntity.ok().body(
                    userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS))
            );
    }

    @GetMapping("/myInfor")
    public ResponseEntity<?> getUserInfor() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String email = authentication.getName();
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        return ResponseEntity.ok().body(
                ApiResponseDTO.<UserEntity>builder()
                        .result(user)
                        .build()
        );
    }
}
