package com.javanc.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.benmanes.caffeine.cache.Cache;
import com.javanc.controlleradvice.customeException.AppException;
import com.javanc.controlleradvice.customeException.UserNotExistsException;
import com.javanc.enums.ErrorCode;
import com.javanc.model.request.AuthenRequest;
import com.javanc.model.request.auth.RegisterRequest;

import com.javanc.model.request.client.CheckOTPRequest;
import com.javanc.model.request.client.OTPRequest;
import com.javanc.model.request.client.ResetPasswordRequest;
import com.javanc.model.response.AuthenResponse;
import com.javanc.model.response.admin.AccountAdminResponse;
import com.javanc.repository.*;
import com.javanc.repository.entity.*;
import com.javanc.service.AuthenService;
import com.javanc.service.EmailService;
import com.javanc.service.RedisService;
import com.javanc.service.UploadImageFileService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
@PropertySource("classpath:application.yml")
@Slf4j
public class AuthenServiceImpl implements AuthenService {


    @NonFinal
    @Value("${jwt.sign_key}")
    String SIGN_KEY;

    @NonFinal
    @Value("${jwt.expiry_time_token}")
    private Long EXPIRY_TIME_TOKEN;

    @NonFinal
    @Value("${jwt.expiry_time_refreshToken}")
    private Long EXPIRY_TIME_REFRESH_TOKEN;

    Cache<String, List<AccountAdminResponse>> cache;
    Cache<String, Boolean> tokenAllowListCache;


    UserRepository userRepository;
    RoleRepository roleRepository;
    AddressRepository addressRepository;
    UserAddressRepository userAddressRepository;

    PasswordEncoder passwordEncoder;
    UploadImageFileService uploadImageFileService;
    RedisService redisService;
    EmailService emailService;

    private String generateToken(UserEntity user) {
        // thuật toán mã hóa
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        String token_id = UUID.randomUUID().toString();
        // data trong body
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                // user đăng nhập
                .subject(user.getEmail())
                .issuer("duytrinh.com")
                // thời gian ký
                .issueTime(new Date())
                // thời gian hết hạn
                .expirationTime(new Date(
                        Instant.now().plus(EXPIRY_TIME_TOKEN, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .claim("scope", buildScope(user))
                // Lưu để lấy thông tin token
                .jwtID(token_id)
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGN_KEY.getBytes()));
            tokenAllowListCache.put(token_id, true);
            return jwsObject.serialize();
        } catch (Exception e) {
            log.error("Cannot generate token", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public AuthenResponse login(AuthenRequest authenRequest) {
        UserEntity user = userRepository.findByEmail(authenRequest.getEmail()).orElseThrow(
                () -> new UserNotExistsException("User not found")
        );
        boolean authenticated = passwordEncoder.matches(authenRequest.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new RuntimeException("Tài khoản hoặc mật khẩu không chính xác");
        }
        if (!user.getStatus() || user.getDeleted()) {
            throw new AppException(ErrorCode.ACCOUNT_INACTIVE);
        }
        String token = generateToken(user);

        return AuthenResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public void sendEmail(RegisterRequest registerRequest) throws IOException, MessagingException {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        String src = uploadImageFileService.uploadImage(registerRequest.getAvatar());
        registerRequest.setSrc(src);
        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
        redisService.savePendingUser(registerRequest.getEmail(), otp, registerRequest);
        emailService.sendSimpleEmail(registerRequest.getEmail(), otp);
    }

    @Override
    public AuthenResponse introspect(AuthenRequest authenRequest) throws JOSEException, ParseException {
        var token = authenRequest.getToken();
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }
        return AuthenResponse.builder()
                .valid(isValid)
                .token(token)
                .build();
    }

    @Override
    public AuthenResponse refreshToken(AuthenRequest authenRequest) throws ParseException, JOSEException {
        // Authentication token
        SignedJWT singedJWT = verifyToken(authenRequest.getToken(), true);
        // get id and expiry time to create InvalidToken
        String jti = singedJWT.getJWTClaimsSet().getJWTID();

        tokenAllowListCache.invalidate(jti);

        // create new token
        String email = singedJWT.getJWTClaimsSet().getSubject();
        UserEntity user = userRepository.findByEmail(email).orElseThrow(()
                -> new AppException(ErrorCode.USER_NOT_EXISTS));
        String token = generateToken(user);
        return AuthenResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public void register(AuthenRequest authenRequest) throws JsonProcessingException {
        if (userRepository.existsByEmail(authenRequest.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        RegisterRequest registerRequest = redisService.getPendingUser(authenRequest.getEmail(), authenRequest.getOtp());
        if (registerRequest == null) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
        RoleEntity role = roleRepository.findByCode("USER").orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
        );

        UserEntity newUser = UserEntity.builder()
                .email(registerRequest.getEmail())
                .name(registerRequest.getName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(List.of(role))
                .avatar(registerRequest.getSrc())
                .phone(registerRequest.getPhone())
                .build();

        AddressEntity newAddress = AddressEntity.builder()
                .cityId(registerRequest.getCityId())
                .districtId(registerRequest.getDistrictId())
                .wardId(registerRequest.getWardId())
                .detail(registerRequest.getDetail())
                .build();

        userRepository.save(newUser);
        addressRepository.save(newAddress);

        UserAddressEntity userAddress = UserAddressEntity.builder()
                .user(newUser)
                .address(newAddress)
                .isDefault(true)
                .build();
        userAddressRepository.save(userAddress);
    }

    @Override
    public AuthenResponse loginWithGoogleOrFacebook(Map<String, Object> info) {
        String email = (String) info.get("email");
        String name = (String) info.get("name");
        String avatar = (String) info.get("avatar");

        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        UserEntity user = null;
        if (userEntity.isPresent()) {
            user = userEntity.get();
            if (user.getDeleted() || !user.getStatus()) {
                throw new AppException(ErrorCode.ACCOUNT_INACTIVE);
            }
        } else {
            RoleEntity role = roleRepository.findByCode("USER").orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );
            user = UserEntity.builder()
                    .email(email)
                    .name(name)
                    .roles(List.of(role))
                    .avatar(avatar)
                    .build();
            userRepository.save(user);
            cache.invalidate("listAccounts");
        }
        String token = generateToken(user);
        return AuthenResponse.builder()
                .token(token)
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .build();
    }

    @Override
    public void logout(String token) {
        try {
            // xac minh token
            SignedJWT signedJWT = verifyToken(token, false);
            // lay id token
            String jti = signedJWT.getJWTClaimsSet().getJWTID();
            // thoi gian het han token
            Date expiratime = signedJWT.getJWTClaimsSet().getExpirationTime();
            tokenAllowListCache.invalidate(jti);
        } catch (AppException exception) {
            log.info("Token already expired");
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void sendForgotPasswordOTP(OTPRequest otpRequest) throws JsonProcessingException ,MessagingException{
        String email = otpRequest.getEmail();
        if(!userRepository.findByEmail(email).isPresent()){
            throw new AppException(ErrorCode.USER_NOT_EXISTS);
        }
        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
        redisService.savePendingUserOTP(email, otp, otpRequest);
        emailService.sendSimpleEmail(email, otp);


    }

    @Override
    public boolean checkForgotPasswordOTP(CheckOTPRequest checkOTPRequest) throws ParseException, JOSEException {
        String storeOtp = redisService.getOTP(checkOTPRequest.getEmail());
        if(storeOtp == null || !storeOtp.equals(checkOTPRequest.getOtp())){
            return false;
        }

        // Optional: có thể xoá OTP ngay sau khi verify
        redisService.deleteOTP(checkOTPRequest.getEmail());
        return true;

    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) throws ParseException, JOSEException {
        UserEntity user = userRepository.findByEmail(resetPasswordRequest.getEmail()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        String hashedPassword = passwordEncoder.encode(resetPasswordRequest.getNewPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }



    // kiem tra tinh hop le cua token (sai / het han)
    // SignedJWT: đại diện cho 1 token đã được ký
    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {

        // xác thực token với thuận toán HMAC (SIGN_KEY.getBytes(): khóa dùng để xác minh chữ ký của JWT)
        JWSVerifier verifier = new MACVerifier(SIGN_KEY.getBytes());

        // chuyển chuỗi JWT thành đối tượng SingedJWT để trích xuất thông tin
        SignedJWT signedJWT = SignedJWT.parse(token);
        // lấy thời gian hết hạn từ token
        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                .toInstant().plus(EXPIRY_TIME_REFRESH_TOKEN, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        // xác thực token với thuật toán HMAC
        boolean verified = signedJWT.verify(verifier);

        // token sai hoac het han
        if (!(verified && expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if (tokenAllowListCache.getIfPresent(signedJWT.getJWTClaimsSet().getJWTID()) == null && !isRefresh) {
            throw new AppException(ErrorCode.ACCOUNT_INACTIVE);
        }
        return signedJWT;
    }


    private String buildScope(UserEntity user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!user.getRoles().isEmpty()) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getCode());
                if (!role.getPermissions().isEmpty()) {
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add((permission.getName()));
                    });
                }
            });
        }
        return stringJoiner.toString();
    }
}
