package com.javanc.service.impl;

import com.javanc.controlleradvice.customeException.AppException;
import com.javanc.enums.ErrorCode;
import com.javanc.model.request.admin.PermissionAdminRequest;
import com.javanc.model.response.PermissionResponse;
import com.javanc.repository.PermissionRepository;
import com.javanc.repository.RoleRepository;
import com.javanc.repository.entity.PermissionEntity;
import com.javanc.repository.entity.RoleEntity;
import com.javanc.service.PermissionService;
import com.javanc.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor

public class PermissionServiceImpl implements PermissionService {

    PermissionRepository permissionRepository;
    RoleRepository roleRepository;
    RoleService roleService;

    @Override
    public List<PermissionResponse> listPermissions() {
        List<PermissionEntity> list = permissionRepository.findAll();
        List<PermissionResponse> responseList = new ArrayList<>();
        for (PermissionEntity entity : list) {
            PermissionResponse response = PermissionResponse.builder()
                    .name(entity.getName())
                    .description(entity.getDescription())
                    .build();
            responseList.add(response);
        }
        return responseList;
    }


    @Override
    public void updatePermissions(List<PermissionAdminRequest> listPermissionAdminRequest) {
        for (PermissionAdminRequest permission : listPermissionAdminRequest) {
            RoleEntity role = roleRepository.findById(permission.getRoleId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );
            roleService.removeAllPermissionsFromRole(role.getId());
            if(!permission.getPermissions().isEmpty()){
                for(String namePermission : permission.getPermissions()){
                    PermissionEntity permissionEntity = permissionRepository.findByName(namePermission).orElseThrow(
                            () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
                    );
                    role.getPermissions().add(permissionEntity);
                }
            }
            roleRepository.save(role);
        }
    }
}
