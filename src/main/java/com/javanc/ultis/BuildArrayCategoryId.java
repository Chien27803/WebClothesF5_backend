package com.javanc.ultis;

import com.javanc.repository.CategoryRepository;
import com.javanc.repository.entity.CategoryEntity;
import com.javanc.service.CategoryService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class BuildArrayCategoryId {

     CategoryRepository categoryRepository;

    public List<Long> getAllChildCategoryId(Long parentId) {
        List<Long> result = new ArrayList<Long>();
        result.add(parentId);
        List<CategoryEntity> listCategories = categoryRepository.findAll();
        findChildren(parentId, listCategories, result);
        return result;
    }
    private void findChildren(Long parentId, List<CategoryEntity> listCategories, List<Long> result) {
        for (CategoryEntity category : listCategories) {
            if(category.getParentId().equals(parentId)) {
                result.add(category.getId());
                findChildren(category.getId(), listCategories, result);
            }
        }
    }
}
