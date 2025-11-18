package com.javanc.repository.custom;

import java.util.Map;

public interface FilterAccountCustom {
    Map<String, Object> findAccountByOption(String searchKey, String status, Long pageNumber, String roleId);
}
