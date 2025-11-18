package com.javanc.repository.custom.impl;

import com.javanc.repository.custom.FilterProductCustom;
import com.javanc.repository.entity.ProductEntity;
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
public class FilterProductCustomImpl implements FilterProductCustom {

    @PersistenceContext
    private EntityManager entityManager;
    BuildArrayCategoryId buildArrayCategoryId;

    final Long limitProduct = 4L;

    public void queryNomarl(String searchKey, String status, StringBuilder where){
        if(!searchKey.equals("")) {
            where.append(" and p.slug like '%" + searchKey + "%' ");
        }
        if(!status.equals("")) {
            if(status.equals("active")) {
                where.append(" and p.deleted=0 ");
            }else if (status.equals("inactive")) {
                where.append(" and p.deleted=1 ");
            }
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
    public Map<String, Object> findProductByOption(String searchKey, String categoryId, String status, String pageNumber) {
        StringBuilder sql = new StringBuilder("select p.* from product p ");
        StringBuilder where = new StringBuilder(" where 1=1 ");
        queryNomarl(searchKey, status, where);
        if(!categoryId.equals("")) {
            Long category = NumberUltis.parseLong(categoryId);
            querySpecial(category, where);
        }
        sql.append(where);
        Long totalProducts = (long) entityManager.createNativeQuery(sql.toString()).getResultList().size();
        Long currentPage = NumberUltis.parseLong(pageNumber);
        Long skip = (currentPage - 1) * limitProduct;
        sql.append(" LIMIT ").append(limitProduct);
        sql.append(" OFFSET ").append(skip);
        Query query = entityManager.createNativeQuery(sql.toString(), ProductEntity.class);
        Map<String, Object> result = new HashMap<>();
        result.put("totalProducts", totalProducts);
        result.put("products", query.getResultList());
        result.put("limitProduct", limitProduct);
        return result;
    }
}
