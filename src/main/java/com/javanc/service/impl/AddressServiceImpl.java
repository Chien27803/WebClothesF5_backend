package com.javanc.service.impl;

import com.javanc.controlleradvice.customeException.AppException;
import com.javanc.enums.ErrorCode;
import com.javanc.model.request.client.AddressRequest;
import com.javanc.model.response.client.AddressResponse;
import com.javanc.repository.AddressRepository;
import com.javanc.repository.UserAddressRepository;
import com.javanc.repository.UserRepository;
import com.javanc.repository.entity.AddressEntity;
import com.javanc.repository.entity.UserAddressEntity;
import com.javanc.repository.entity.UserEntity;
import com.javanc.service.AddressService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    AddressRepository addressRepository;
    UserRepository userRepository;
    UserAddressRepository userAddressRepository;

    @Override
    public List<AddressResponse> getAll() {
        return List.of();
    }

    @Override
    public AddressResponse getById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void create(AddressRequest data) {
        SecurityContext context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTS));

        if (Boolean.TRUE.equals(data.getIsDefault())) {
            // Lấy tất cả địa chỉ liên kết với user và set isDefault = false
            List<UserAddressEntity> userAddresses = userAddressRepository.findByUser(user);
            for (UserAddressEntity ua : userAddresses) {
                if (Boolean.TRUE.equals(ua.getIsDefault())) {
                    ua.setIsDefault(false);
                    userAddressRepository.save(ua); // hoặc dùng saveAll sau vòng lặp
                }
            }
        }

        AddressEntity addressEntity = AddressEntity.builder()
                .cityId(data.getCityId())
                .wardId(data.getWardId())
                .districtId(data.getDistrictId())
                .detail(data.getDetail())
                .build();
        addressRepository.save(addressEntity);

        UserAddressEntity userAddressEntity = UserAddressEntity.builder()
                .address(addressEntity)
                .user(user)
                .isDefault(data.getIsDefault())
                .build();
        userAddressRepository.save(userAddressEntity);

    }

    @Override
    @Transactional
    public void update(Long id, AddressRequest data) {
        SecurityContext context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTS));

        if (Boolean.TRUE.equals(data.getIsDefault())) {
            List<UserAddressEntity> userAddresses = userAddressRepository.findByUser(user);
            for (UserAddressEntity ua : userAddresses) {
                if (Boolean.TRUE.equals(ua.getIsDefault())) {
                    ua.setIsDefault(false);
                    userAddressRepository.save(ua); // hoặc dùng saveAll sau vòng lặp
                }
            }
        }

        UserAddressEntity userAddressEntity = userAddressRepository.findByUserAndAddress_id(user, id).orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
        );
        userAddressEntity.setIsDefault(data.getIsDefault());
        userAddressRepository.save(userAddressEntity);
    }
}
