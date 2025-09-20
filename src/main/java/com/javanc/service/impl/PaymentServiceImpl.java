package com.javanc.service.impl;

import com.javanc.config.VNPCofig;
import com.javanc.model.request.client.PaymentRequest;
import com.javanc.service.PaymentService;
import com.javanc.ultis.VNPUntils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {


    VNPCofig vnpCofig;
    VNPUntils vnPayUtils;

    @Override
    public String createPayment(PaymentRequest paymentRequest, HttpServletRequest httpServletRequest) {
        String version = "2.1.0";
        String command = "pay";
        String orderType = "other";
        long amount = paymentRequest.getAmount() * 100; // Số tiền cần nhân với 100

        String transactionReference = vnPayUtils.getRandomNumber(8); // Mã giao dịch
        String clientIpAddress = vnPayUtils.getIpAddress(httpServletRequest);

        String terminalCode = vnpCofig.getVnpTmnCode();

        Map<String, String> params = new HashMap<>();
        params.put("vnp_Version", version);
        params.put("vnp_Command", command);
        params.put("vnp_TmnCode", terminalCode);
        params.put("vnp_Amount", String.valueOf(amount));
        params.put("vnp_CurrCode", "VND");

        params.put("vnp_TxnRef", transactionReference);
        params.put("vnp_OrderInfo", "Thanh toan don hang:" + transactionReference);
        params.put("vnp_OrderType", orderType);

        String locale = paymentRequest.getLanguage();

        if (locale != null && !locale.isEmpty()) {
            params.put("vnp_Locale", locale);
        } else {
            params.put("vnp_Locale", "vn");
        }

        params.put("vnp_ReturnUrl", vnpCofig.getVnpReturnUrl());
        params.put("vnp_IpAddr", clientIpAddress);
        if(paymentRequest.getBankCode() != null){
            params.put("vnp_BankCode", paymentRequest.getBankCode());
        }

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String createdDate = dateFormat.format(calendar.getTime());
        params.put("vnp_CreateDate", createdDate);

        calendar.add(Calendar.MINUTE, 15);
        String expirationDate = dateFormat.format(calendar.getTime());
        params.put("vnp_ExpireDate", expirationDate);

        List<String> sortedFieldNames = new ArrayList<>(params.keySet());
        Collections.sort(sortedFieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder queryData = new StringBuilder();

        for (Iterator<String> iterator = sortedFieldNames.iterator(); iterator.hasNext(); ) {
            String fieldName = iterator.next();
            String fieldValue = params.get(fieldName);

            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                queryData.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII))
                        .append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (iterator.hasNext()) {
                    hashData.append('&');
                    queryData.append('&');
                }
            }
        }

        String secureHash = vnPayUtils.hmacSHA512(vnpCofig.getSecretKey(), hashData.toString());
        queryData.append("&vnp_SecureHash=").append(secureHash);

        return vnpCofig.getVnpPayUrl() + "?" + queryData;
    }
}
