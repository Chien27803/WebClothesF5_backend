package com.javanc.repository;

import com.javanc.repository.entity.ChatRoomEntity;
import com.javanc.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
    Optional<ChatRoomEntity> findByUserAndAdmin(UserEntity user, UserEntity admin);
    Optional<ChatRoomEntity> findById(Long id);
    List<ChatRoomEntity> findAllByAdmin(UserEntity admin);
}
