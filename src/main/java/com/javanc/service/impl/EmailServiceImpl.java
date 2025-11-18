package com.javanc.service.impl;

import com.javanc.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendSimpleEmail(String to, String otpCode) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("Xác thực tài khoản - Mã OTP của bạn");

        String htmlContent = "<div style='font-family: Arial, sans-serif; padding: 20px;'>"
                + "<h2 style='color: #2e6c80;'>Xác minh tài khoản của bạn</h2>"
                + "<p>Chào bạn,</p>"
                + "<p>Đây là mã OTP để xác thực tài khoản của bạn:</p>"
                + "<div style='font-size: 24px; font-weight: bold; color: #e74c3c; margin: 20px 0;'>"
                + otpCode
                + "</div>"
                + "<p>Mã OTP này sẽ hết hạn sau 5 phút. Vui lòng không chia sẻ mã với bất kỳ ai.</p>"
                + "<p>Trân trọng,<br>Đội ngũ hỗ trợ</p>"
                + "</div>";

        helper.setText(htmlContent, true); // true = HTML

        mailSender.send(message);
    }
}
