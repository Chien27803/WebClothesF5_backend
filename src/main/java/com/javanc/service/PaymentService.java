package com.javanc.service;

import com.javanc.model.request.client.PaymentRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    String createPayment(PaymentRequest paymentRequest, HttpServletRequest httpServletRequest);
}
