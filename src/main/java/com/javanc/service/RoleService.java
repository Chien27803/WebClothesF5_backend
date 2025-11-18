package com.javanc.service;

import com.javanc.model.request.admin.RoleAdminRequest;
import com.javanc.repository.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleService extends IService<RoleAdminRequest, RoleEntity, Long>{
    void removeAllPermissionsFromRole(Long roleId);
}
