package com.javanc.repository;

import com.javanc.repository.entity.ShoppingCartEntity;
import com.javanc.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {
    Optional<ShoppingCartEntity> findByUser(UserEntity user);
    void deleteByUser(UserEntity user);
}
