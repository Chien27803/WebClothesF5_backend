package com.javanc.repository.custom.impl;

import com.javanc.repository.custom.FilterProductUserCustom;
import com.javanc.repository.entity.ProductEntity;
import com.javanc.repository.entity.UserEntity;
import com.javanc.ultis.BuildArrayCategoryId;
import com.javanc.ultis.NumberUltis;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FilterProductUserCustomImpl implements FilterProductUserCustom {

    @PersistenceContext
    private EntityManager entityManager;
    BuildArrayCategoryId buildArrayCategoryId;

    public void queryNomarl(String searchKey, StringBuilder where){
        if(!searchKey.trim().equals("")) {
            where.append(" and p.slug like '%" + searchKey + "%' ");
        }
    }

    public void querySpecial(Long parentId, StringBuilder where) {
        List<Long> categoryIds = buildArrayCategoryId.getAllChildCategoryId(parentId);
        if(!categoryIds.isEmpty()) {
            String values = categoryIds.stream().map(categoryId -> "'" + categoryId + "'").collect(Collectors.joining(","));
            where.append(" and p.category_id in (" + values + ")");
        }
    }


    @Override
    public List<ProductEntity> findProductByOptionForUser(Long categoryId, String searchKey) {
        StringBuilder sql = new StringBuilder("select p.* from product p ");
        StringBuilder where = new StringBuilder(" where 1=1 and deleted=0 ");
        queryNomarl(searchKey, where);
        if(categoryId != null) {
            querySpecial(categoryId, where);
        }
        sql.append(where);
        Query query = entityManager.createNativeQuery(sql.toString(), ProductEntity.class);
        return query.getResultList();
    }
}
