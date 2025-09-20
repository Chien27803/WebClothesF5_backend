package com.javanc.repository;

import com.javanc.repository.entity.ProductShoppingCartEntity;
import com.javanc.repository.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductShoppingCartRepository extends JpaRepository<ProductShoppingCartEntity, Long> {
    List<ProductShoppingCartEntity> findAllByShoppingCart_id(Long id);
    void deleteAllByShoppingCart(ShoppingCartEntity shoppingCart);
    List<ProductShoppingCartEntity> findByShoppingCart(ShoppingCartEntity shoppingCart);
}
