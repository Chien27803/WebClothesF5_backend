package com.javanc.repository;

import com.javanc.repository.entity.NotificationEntity;
import com.javanc.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findAllByUser(UserEntity user);
}
