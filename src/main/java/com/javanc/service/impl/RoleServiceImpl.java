package com.javanc.service.impl;


import com.javanc.controlleradvice.customeException.AppException;
import com.javanc.enums.ErrorCode;
import com.javanc.model.request.admin.RoleAdminRequest;
import com.javanc.repository.RoleRepository;
import com.javanc.repository.entity.RoleEntity;
import com.javanc.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;


    @Override
    public void removeAllPermissionsFromRole(Long roleId) {
        RoleEntity role = roleRepository.findById(roleId).orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
        );
        role.getPermissions().clear();
        roleRepository.save(role);
    }

    @Override
    public List<RoleEntity> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public RoleEntity getById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
        RoleEntity role = roleRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
        );
        role.setDeleted(!role.getDeleted());
        roleRepository.save(role);
    }

    @Override
    public void create(RoleAdminRequest data) {
        Optional<RoleEntity> role = roleRepository.findByCode(data.getCode());
        if(role.isPresent()){
            throw new RuntimeException("Quyền với mã " + data.getCode() + " đã tồn tại");
        }
        RoleEntity newRole = RoleEntity.builder()
                .name(data.getName())
                .description(data.getDescription())
                .code(data.getCode())
                .deleted(false)
                .build();
        roleRepository.save(newRole);
    }

    @Override
    public void update(Long id, RoleAdminRequest data) {
        RoleEntity role = roleRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
        );
        role.setName(data.getName());
        role.setDescription(data.getDescription());
        role.setCode(data.getCode());
        roleRepository.save(role);
    }
}
