package com.javanc.service;

import com.javanc.model.response.admin.OrderAdminResponse;

import java.util.List;

public interface OrderAdminService {
    List<OrderAdminResponse> getAllOrders();
    void updateStatus(Long orderId, String status);
    OrderAdminResponse getOrderById(Long orderId);
}
