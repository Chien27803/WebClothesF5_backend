package com.javanc.service.impl;

import com.javanc.controlleradvice.customeException.AppException;
import com.javanc.enums.ErrorCode;
import com.javanc.model.request.admin.CategoryAdminRequest;
import com.javanc.model.response.CategoryResponse;
import com.javanc.repository.CategoryRepository;
import com.javanc.repository.entity.CategoryEntity;
import com.javanc.service.CategoryService;
import com.javanc.ultis.BuildTreeCategory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;


    @Override
    public List<CategoryResponse> getAll() {
        List<CategoryEntity> listCategory = categoryRepository.findAll();
        return BuildTreeCategory.treeCategory(listCategory, 0L);
    }

    @Override
    public CategoryResponse getById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
        return CategoryResponse.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .build();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void create(CategoryAdminRequest request) {
        CategoryEntity category = CategoryEntity.builder()
                .name(request.getName())
                .parentId(request.getParentId())
                .build();
        categoryRepository.save(category);
    }

    @Override
    public void update(Long id, CategoryAdminRequest request) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
        categoryEntity.setName(request.getName());
        categoryRepository.save(categoryEntity);
    }
}
