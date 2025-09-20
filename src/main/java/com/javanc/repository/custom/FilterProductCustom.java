package com.javanc.repository.custom;

import com.javanc.repository.entity.ProductEntity;

import java.util.List;
import java.util.Map;

public interface FilterProductCustom {
    Map<String, Object> findProductByOption(String searchKey, String categoryId, String status, String pageNumber);
}
