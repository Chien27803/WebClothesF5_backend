package com.javanc.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendSimpleEmail(String to, String otpCode) throws MessagingException;
}
