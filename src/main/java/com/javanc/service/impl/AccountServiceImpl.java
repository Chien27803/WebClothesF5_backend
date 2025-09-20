package com.javanc.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.javanc.controlleradvice.customeException.AppException;
import com.javanc.enums.ErrorCode;
import com.javanc.model.request.admin.AccountAdminRequest;
import com.javanc.model.response.admin.AccountPaginationResponse;
import com.javanc.model.response.admin.AccountAdminResponse;
import com.javanc.model.response.admin.RoleResponse;
import com.javanc.repository.RoleRepository;
import com.javanc.repository.UserRepository;
import com.javanc.repository.custom.FilterAccountCustom;
import com.javanc.repository.entity.RoleEntity;
import com.javanc.repository.entity.UserEntity;
import com.javanc.service.AccountService;
import com.javanc.service.UploadImageFileService;
import com.javanc.ultis.NumberUltis;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    UploadImageFileService uploadImageFileService;
    PasswordEncoder passwordEncoder;
    Cache<String, List<AccountAdminResponse>> cache;
    FilterAccountCustom filterAccountCustom;


    @Override
    public AccountPaginationResponse getAccountList(String searchKey, String status, Long pageNumber, String roleId) {
        Map<String, Object> filteredAccount = filterAccountCustom.findAccountByOption(searchKey, status, pageNumber, roleId);
        List<AccountAdminResponse> listAccount = new ArrayList<>();

        @SuppressWarnings("unchecked")
        List<UserEntity> listUser = (List<UserEntity>) filteredAccount.get("accounts");
        Long totalAccounts = (Long) filteredAccount.get("totalAccounts");
        Long limitAccount = (Long) filteredAccount.get("limitAccount");

        for (UserEntity userEntity : listUser) {
            RoleEntity role = userEntity.getRoles().get(0);
            AccountAdminResponse accountAdminResponse = AccountAdminResponse.builder()
                    .id(userEntity.getId())
                    .name(userEntity.getName())
                    .email(userEntity.getEmail())
                    .avatar(userEntity.getAvatar())
                    .created(userEntity.getCreatedAt())
                    .deleted(userEntity.getDeleted())
                    .status(userEntity.getStatus())
                    .role(RoleResponse.builder()
                            .role_id(role.getId())
                            .name(role.getName())
                            .code(role.getCode())
                            .build())
                    .build();
            listAccount.add(accountAdminResponse);
        }
        return AccountPaginationResponse.builder()
                .accounts(listAccount)
                .limitAccount(limitAccount)
                .totalAccounts(totalAccounts)
                .build();
    }

    @Override
    public List<AccountAdminResponse> getAll() {
        return cache.get("listAccounts", k -> userRepository.findAll()
                .stream().map(item -> AccountAdminResponse.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .email(item.getEmail())
                        .avatar(item.getAvatar())
                        .deleted(item.getDeleted())
                        .status(item.getStatus())
                        .created(item.getCreatedAt())
                        .role(RoleResponse.builder()
                                .role_id(item.getRoles().get(0).getId())
                                .name(item.getRoles().get(0).getName())
                                .code(item.getRoles().get(0).getCode())
                                .build())
                        .build())
                .collect(Collectors.toList()));
    }

    @Override
    public AccountAdminResponse getById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTS)
        );
        userEntity.setStatus(!userEntity.getStatus());
        userRepository.save(userEntity);
        cache.invalidate("listAccounts");
    }

    @Override
    public void create(AccountAdminRequest accountAdminRequest)  {
        Optional<UserEntity> userEntity = userRepository.findByEmail(accountAdminRequest.getEmail());
        if (userEntity.isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        String avatar = null;
        try{
             avatar = uploadImageFileService.uploadImage(accountAdminRequest.getAvatar());
        }catch (IOException e){
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
        Long role_id = NumberUltis.parseLong(accountAdminRequest.getRole_id());

        RoleEntity role = roleRepository.findById(role_id).orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
        );

        UserEntity newUser = UserEntity.builder()
                .email(accountAdminRequest.getEmail())
                .avatar(avatar)
                .roles(List.of(role))
                .deleted(false)
                .name(accountAdminRequest.getUsername())
                .password(passwordEncoder.encode(accountAdminRequest.getPassword()))
                .build();
        userRepository.save(newUser);
        cache.invalidate("listAccounts");
    }

    @Override
    public void update(Long id, AccountAdminRequest accountAdminRequest) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTS)
        );

        Long role_id = NumberUltis.parseLong(accountAdminRequest.getRole_id());
        RoleEntity role = roleRepository.findById(role_id).orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
        );
        userEntity.getRoles().clear();
        userEntity.getRoles().add(role);
        userRepository.save(userEntity);
        cache.invalidate("listAccounts");
    }
}
