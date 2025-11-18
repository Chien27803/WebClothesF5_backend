package com.javanc.ultis;

import com.javanc.model.response.CategoryResponse;
import com.javanc.repository.entity.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

public class BuildTreeCategory {

    public static List<CategoryResponse> treeCategory(List<CategoryEntity> categories, Long parentId) {
        List<CategoryResponse> result = new ArrayList<>();
        for (CategoryEntity category : categories) {
            if(category.getParentId().equals(parentId)) {
                CategoryResponse node = CategoryResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .deleted(false)
                        .products(new ArrayList<>())
                        .parentId(category.getParentId())
                        .children(treeCategory(categories, category.getId()))
                        .build();
                result.add(node);
            }
        }
        return result;
    };
}
