package com.javanc.repository;

import com.javanc.repository.entity.FavoriteEntity;
import com.javanc.repository.entity.ProductFavoriteEntity;
import com.javanc.repository.entity.ProductShoppingCartEntity;
import com.javanc.repository.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductFavoriteRepository extends JpaRepository<ProductFavoriteEntity,Long> {
    List<ProductFavoriteEntity> findAllByFavorite_id(Long id);
    void deleteAllByFavorite(FavoriteEntity favorite);
    List<ProductFavoriteEntity> findByFavorite(FavoriteEntity favorite);
}
