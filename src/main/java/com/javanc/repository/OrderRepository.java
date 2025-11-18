package com.javanc.repository;

import com.javanc.model.response.client.OrderResponse;
import com.javanc.repository.entity.AddressEntity;
import com.javanc.repository.entity.OrderEntity;
import com.javanc.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    boolean existsByAddress(AddressEntity address);
    List<OrderEntity> findByUser(UserEntity user);
    List<OrderEntity> findByDeletedFalse();


}
