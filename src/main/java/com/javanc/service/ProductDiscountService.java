package com.javanc.service;

import com.javanc.model.request.admin.DiscountAdminRequest;
import com.javanc.model.response.admin.DiscountAdminResponse;
import com.javanc.repository.entity.DiscountEntity;
import com.javanc.repository.entity.ProductDiscountEntity;

import java.util.List;

public interface ProductDiscountService {
    List<DiscountAdminResponse> findAllByProductId(Long productId);
    void createProductDisconut(DiscountAdminRequest discountAdminRequest);
    void deleteProductDiscountByDiscountId(Long id);
}
