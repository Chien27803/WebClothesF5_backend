package com.javanc.repository;

import com.javanc.repository.entity.PaymentEntity;
import com.javanc.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    Optional<PaymentEntity> findByCodeAndUser(String code, UserEntity user);
}
