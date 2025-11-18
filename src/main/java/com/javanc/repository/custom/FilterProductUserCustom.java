package com.javanc.repository.custom;

import com.javanc.repository.entity.ProductEntity;

import java.util.List;

public interface FilterProductUserCustom {
    List<ProductEntity> findProductByOptionForUser(Long categoryId, String searchKey);
}
