package com.javanc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javanc.model.request.AuthenRequest;
import com.javanc.model.request.auth.RegisterRequest;

import com.javanc.model.request.client.CheckOTPRequest;
import com.javanc.model.request.client.OTPRequest;
import com.javanc.model.request.client.ResetPasswordRequest;
import com.javanc.model.response.AuthenResponse;
import com.nimbusds.jose.JOSEException;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public interface AuthenService {
    AuthenResponse login(AuthenRequest authenRequest);
    void sendEmail(RegisterRequest registerRequest) throws IOException, MessagingException;
    AuthenResponse introspect(AuthenRequest authenRequest) throws JOSEException, ParseException;
    AuthenResponse refreshToken(AuthenRequest authenRequest) throws ParseException, JOSEException;
    void register(AuthenRequest authenRequest) throws JsonProcessingException;
    AuthenResponse loginWithGoogleOrFacebook(Map<String, Object> info);
    void logout(String token);
    void sendForgotPasswordOTP(OTPRequest otpRequest) throws JsonProcessingException,MessagingException;
    boolean checkForgotPasswordOTP(CheckOTPRequest checkOTPRequest) throws ParseException, JOSEException;
    void resetPassword(ResetPasswordRequest resetPasswordRequest) throws ParseException, JOSEException;
}
