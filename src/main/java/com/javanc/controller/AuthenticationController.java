package com.javanc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javanc.controlleradvice.customeException.AppException;
import com.javanc.enums.ErrorCode;
import com.javanc.model.request.AuthenRequest;
import com.javanc.model.request.auth.RegisterRequest;
import com.javanc.model.request.client.AddressRequest;
import com.javanc.model.request.client.InformationUserUpdateRequest;

import com.javanc.model.request.client.*;

import com.javanc.model.response.ApiResponseDTO;
import com.javanc.model.response.AuthenResponse;
import com.javanc.model.response.ProfileResponse;
import com.javanc.model.response.client.AddressResponse;

import com.javanc.model.response.client.InformationUserUpdateResponse;
import com.javanc.repository.AddressRepository;
import com.javanc.repository.UserAddressRepository;
import com.javanc.repository.UserRepository;
import com.javanc.repository.entity.AddressEntity;
import com.javanc.repository.entity.UserAddressEntity;
import com.javanc.repository.entity.UserEntity;
import com.javanc.service.AuthenService;
import com.javanc.service.OAuthService;
import com.nimbusds.jose.JOSEException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    AuthenService authenService;
    OAuthService oAuthService;
    UserRepository userRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;
    @Autowired
    private AddressRepository addressRepository;



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenRequest authenRequest) {
        AuthenResponse response = authenService.login(authenRequest);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("ROLE: " + auth.getAuthorities().toString());
        return ResponseEntity.ok().body(ApiResponseDTO.<AuthenResponse>builder().result(response).build());

    }

    @PostMapping("/introspect")
    public ResponseEntity<?> introspect(@RequestBody AuthenRequest authenRequest) throws JOSEException, ParseException {
        AuthenResponse response = authenService.introspect(authenRequest);
        return ResponseEntity.ok().body(ApiResponseDTO.<AuthenResponse>builder().result(response).build());
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody AuthenRequest authenRequest) throws ParseException, JOSEException {
        return ResponseEntity.ok().body(ApiResponseDTO.<AuthenResponse>builder().result(authenService.refreshToken(authenRequest)).build());

    }

    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@Valid @ModelAttribute RegisterRequest registerRequest, BindingResult result) throws IOException, MessagingException {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors() // lấy các field lỗi
                    .stream().map(FieldError::getDefaultMessage) // lấy message của từng field bị lỗi
                    .collect(Collectors.toList());

            ApiResponseDTO<List<String>> apiResponseDTO = ApiResponseDTO.<List<String>>builder().code(400).result(errorMessages).build();
            return ResponseEntity.badRequest().body(apiResponseDTO);
        }
        authenService.sendEmail(registerRequest);
        return ResponseEntity.ok().body(ApiResponseDTO.<String>builder().message("Vui lòng kiểm tra email").build());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthenRequest authenRequest) throws JsonProcessingException {
        authenService.register(authenRequest);
        return ResponseEntity.ok().body(
                ApiResponseDTO.<Void>builder()
                        .message("Ok")
                        .build()
        );
    }
    @PostMapping("/forgot/OTPRequest")
    public ResponseEntity<?> forgot( @Valid @RequestBody OTPRequest otpRequest , BindingResult result) throws IOException, MessagingException {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors() // lấy các field lỗi
                    .stream().map(FieldError::getDefaultMessage) // lấy message của từng field bị lỗi
                    .collect(Collectors.toList());
            ApiResponseDTO<List<String>> apiResponseDTO = ApiResponseDTO.<List<String>>builder()
                    .code(400)
                    .result(errorMessages)
                    .build();
            return ResponseEntity.badRequest().body(apiResponseDTO);
        }
        authenService.sendForgotPasswordOTP(otpRequest);
        return ResponseEntity.ok(ApiResponseDTO.<String>builder()
                .message("OTP đã được gửi về email")
                .build());
    }
    @PostMapping("/forgot/checkOTP")
    public ResponseEntity<?> checkOTP(@Valid @RequestBody CheckOTPRequest checkOTPRequest, BindingResult result) throws IOException, MessagingException {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(ApiResponseDTO.<List<String>>builder()
                    .code(400)
                    .result(errors)
                    .build());
        }
        boolean valid;
        String kq;
        try{
            valid = authenService.checkForgotPasswordOTP(checkOTPRequest);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(valid){
            kq="OTP hợp lệ";
            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .code(200)
                    .result(kq)
                    .build());

        }
        else{
            kq="OTP ko hợp lệ";
            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .code(599)
                    .result(kq)
                    .build());
        }


    }
    @PatchMapping("/forgot/reset")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest, BindingResult result) throws IOException, MessagingException {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(ApiResponseDTO.<List<String>>builder()
                    .code(400)
                    .result(errors)
                    .build());
        }
        try{

            authenService.resetPassword(resetPasswordRequest);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(ApiResponseDTO.<String>builder()
                .message("Đổi mật khẩu thành công")
                .build());
    }

    @GetMapping("/social-login")
    public ResponseEntity<?> socialLogin(@RequestParam("type") String type) {
        String url = oAuthService.generateAuthorizationURL(type);
        return ResponseEntity.ok().body(ApiResponseDTO.<Void>builder().message(url).build());

    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String email = authentication.getName();
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        return ResponseEntity.ok().body(ApiResponseDTO.<ProfileResponse>builder().result(ProfileResponse.builder().id(user.getId()).name(user.getName()).avatar(user.getAvatar()).email(user.getEmail()).status(user.getStatus()).phone(user.getPhone()).addresses(user.getAddresses().stream().map(item -> AddressResponse.builder()

                .userAddressId(item.getId())

                .addressId(item.getAddress().getId()).userId(user.getId()).cityId(item.getAddress().getCityId()).wardId(item.getAddress().getWardId()).districtId(item.getAddress().getDistrictId()).detail(item.getAddress().getDetail()).isDefault(item.getIsDefault()).build()).collect(Collectors.toList())).build()).build());
    }

    @PatchMapping("/profile")
    public ResponseEntity<?> setProfile(@RequestBody InformationUserUpdateRequest informationUserUpdateRequest) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String email = authentication.getName();

        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        user.setAvatar(informationUserUpdateRequest.getAvatar());

        user.setName(informationUserUpdateRequest.getName());
        user.setEmail(informationUserUpdateRequest.getEmail());
        user.setPhone(informationUserUpdateRequest.getPhone());
        userRepository.save(user);


        List<UserAddressEntity> userAddressEntities = informationUserUpdateRequest.getAddressRequestList().stream().map(req -> {
            AddressEntity addressEntity;
            if (req.getAddressId() != null) {
                addressEntity = addressRepository.findById(req.getAddressId()).orElseThrow(() -> new AppException(ErrorCode.USER_ADDRESS_NOT_FOUND)); // xử lý đúng
                addressEntity.setCityId(req.getCityId());
                addressEntity.setDistrictId(req.getDistrictId());
                addressEntity.setWardId(req.getWardId());
                addressEntity.setDetail(req.getDetail());
                addressRepository.save(addressEntity);
            } else {
                addressEntity = AddressEntity.builder().cityId(req.getCityId()).districtId(req.getDistrictId()).wardId(req.getWardId()).detail(req.getDetail()).build();
                addressRepository.save(addressEntity);
            }

            UserAddressEntity userAddressEntity;
            if (req.getUserAddressId() != null) {
                userAddressEntity = userAddressRepository.findById(req.getUserAddressId()).orElseThrow(() -> new AppException(ErrorCode.USER_ADDRESS_NOT_FOUND));
                userAddressEntity.setAddress(addressEntity);
                userAddressEntity.setUser(user);
            } else {
                userAddressEntity = UserAddressEntity.builder().user(user).address(addressEntity).build();
            }

            return userAddressEntity;
        }).collect(Collectors.toList());


        userAddressRepository.saveAll(userAddressEntities);

        List<InformationUserUpdateResponse.AddressUpdateDTO> addressUpdateDTOS = new ArrayList<>();

        for (UserAddressEntity ua : userAddressEntities) {
            AddressEntity addressEntity = ua.getAddress();

            InformationUserUpdateResponse.AddressUpdateDTO dto = InformationUserUpdateResponse.AddressUpdateDTO.builder().userAddressId(ua.getId()).addressId(addressEntity.getId()).cityId(addressEntity.getCityId()).districtId(addressEntity.getDistrictId()).wardId(addressEntity.getWardId()).detail(addressEntity.getDetail())
                    .build();
            addressUpdateDTOS.add(dto);
        }


        InformationUserUpdateResponse informationUserUpdateResponse = InformationUserUpdateResponse.builder().avatar(user.getAvatar()).name(user.getName()).phone(user.getPhone()).email(user.getEmail()).addressUpdateDTOs(addressUpdateDTOS).build();

        return ResponseEntity.ok().body(ApiResponseDTO.<InformationUserUpdateResponse>builder().message("Oke").result(informationUserUpdateResponse).build());
    }


    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        authenService.logout(authHeader.replace("Bearer ", ""));
        return ResponseEntity.ok().body(ApiResponseDTO.<Void>builder().build());
    }
}
