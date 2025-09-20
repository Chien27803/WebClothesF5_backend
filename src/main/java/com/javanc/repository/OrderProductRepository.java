package com.javanc.repository;

import com.javanc.repository.entity.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {
    List<OrderProductEntity> findAllByOrder_id(Long orderId);
}
