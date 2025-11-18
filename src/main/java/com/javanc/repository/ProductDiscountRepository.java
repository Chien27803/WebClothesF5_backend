package com.javanc.repository;

import com.javanc.repository.entity.ProductDiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDiscountRepository extends JpaRepository<ProductDiscountEntity, Long> {
    List<ProductDiscountEntity> findAllByProductId(Long productId);
    void deleteByDiscount_Id(Long discountId);
}
