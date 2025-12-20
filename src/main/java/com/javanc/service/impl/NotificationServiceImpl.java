package com.javanc.service.impl;

import com.javanc.controlleradvice.customeException.AppException;
import com.javanc.enums.ErrorCode;
import com.javanc.repository.NotificationRepository;
import com.javanc.repository.UserRepository;
import com.javanc.repository.entity.NotificationEntity;
import com.javanc.repository.entity.UserEntity;
import com.javanc.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    UserRepository userRepository;
    NotificationRepository notificationRepository;

    @Override
    public void updateStatus(Long userId) {

        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTS));

        List<NotificationEntity> notificationEntities = notificationRepository.findAllByUser((user));
        for (NotificationEntity notificationEntity : notificationEntities) {
            notificationEntity.setStatus(true);
            notificationRepository.save(notificationEntity);
        }
    }
}
