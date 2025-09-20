package com.javanc.service;

import com.javanc.model.request.admin.PermissionAdminRequest;
import com.javanc.model.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    List<PermissionResponse> listPermissions();
    void updatePermissions(List<PermissionAdminRequest> listPermissionAdminRequest);
}
