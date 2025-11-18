package com.javanc.service;

import com.javanc.model.request.admin.AccountAdminRequest;
import com.javanc.model.response.admin.AccountPaginationResponse;
import com.javanc.model.response.admin.AccountAdminResponse;

public interface AccountService extends IService<AccountAdminRequest, AccountAdminResponse, Long>{
    AccountPaginationResponse getAccountList(String searchKey, String status, Long pageNumber, String roleId);
}
