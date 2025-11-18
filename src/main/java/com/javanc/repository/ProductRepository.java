package com.javanc.repository;

import com.javanc.repository.entity.ProductEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByCategory_idInAndDeletedAndSlugContaining(List<Long> categoryIds, Boolean deleted, String slug);

    void deleteAllByIdIn(List<Long> ids);

    List<ProductEntity> findAllByIdIn(List<Long> ids);

    Optional<ProductEntity> findBySlug(String slug);
//    @Query("SELECT p FROM ProductEntity p WHERE (:categoryId IS NULL OR p.category.id = :categoryId) AND p.deleted = :deleted AND p.slug LIKE %:searchKey%")
//    List<ProductEntity> findAllByCategory_idInAndDeletedAndSlugContaining(@Param("categoryId") List<Long> categoryId, @Param("deleted") boolean deleted, @Param("searchKey") String searchKey);
}
