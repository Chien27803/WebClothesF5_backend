package com.javanc.repository;

import com.javanc.repository.entity.FavoriteEntity;
import com.javanc.repository.entity.ShoppingCartEntity;
import com.javanc.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {
    Optional<FavoriteEntity> findByUser(UserEntity user);
    void deleteByUser(UserEntity user);
}
