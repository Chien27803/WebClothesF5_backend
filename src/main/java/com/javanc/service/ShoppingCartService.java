package com.javanc.service;

import com.javanc.model.request.client.ProductCartItemRequest;

import com.javanc.model.response.client.ProductFavoriteResponse;


import java.util.List;

public interface ShoppingCartService {
    void createCart(List<ProductCartItemRequest> productCartItemRequests);
    void updateCart(List<ProductCartItemRequest> productCartItemRequests);
    List<ProductCartItemRequest> getCartByUser();
}
