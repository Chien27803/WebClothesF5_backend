package com.javanc.repository;

import com.javanc.repository.entity.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductOrderRepository extends JpaRepository<OrderProductEntity, Long> {
    List<OrderProductEntity> findByOrderId(Long orderId);

}
