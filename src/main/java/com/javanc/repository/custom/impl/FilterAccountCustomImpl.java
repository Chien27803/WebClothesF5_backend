package com.javanc.repository.custom.impl;

import com.javanc.repository.custom.FilterAccountCustom;
import com.javanc.repository.entity.UserEntity;
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
public class FilterAccountCustomImpl implements FilterAccountCustom {

    @PersistenceContext
    private EntityManager entityManager;

    final Long limitAccount = 4L;

    public void queryNomarl(String searchKey, String status, StringBuilder where) {
        if (!searchKey.equals("")) {
            where.append(" and ( a.name like '%" + searchKey + "%' ");
            where.append(" or a.email like '%" + searchKey + "%' )");
        }
        if (!status.equals("")) {
            if (status.equals("active")) {
                where.append(" and a.status=1 ");
            } else if (status.equals("inactive")) {
                where.append(" and a.status=0 ");
            }
        }
    }

    public void querySpecial(String code, StringBuilder sql, StringBuilder where) {
        if (!code.equals("")) {
            sql.append(" inner join user_role ar on ar.user_id = a.id ");
            sql.append(" inner join role r on r.id = ar.role_id ");
            if (code.equals("USER")) {
                where.append(" and r.code = 'USER'");
            } else {
                where.append(" and r.code != 'USER'");
            }
        }
    }

    @Override
    public Map<String, Object> findAccountByOption(String searchKey, String status, Long pageNumber, String roleId) {
        StringBuilder sql = new StringBuilder("select a.* from user a ");
        StringBuilder where = new StringBuilder(" where 1=1 ");
        queryNomarl(searchKey, status, where);
        querySpecial(roleId, sql, where);
        sql.append(where);
        Long totalAccounts = (long) entityManager.createNativeQuery(sql.toString()).getResultList().size();
        Long currentPage = pageNumber;
        Long skip = (currentPage - 1) * limitAccount;
        sql.append(" LIMIT ").append(limitAccount);
        sql.append(" OFFSET ").append(skip);
        Query query = entityManager.createNativeQuery(sql.toString(), UserEntity.class);
        return Map.of("totalAccounts", totalAccounts, "accounts", query.getResultList(), "limitAccount", limitAccount);
    }
}
