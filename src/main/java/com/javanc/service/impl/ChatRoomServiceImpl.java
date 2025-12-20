package com.javanc.service.impl;

import com.javanc.controlleradvice.customeException.AppException;
import com.javanc.enums.ErrorCode;
import com.javanc.model.response.admin.ChatRoomResponse;
import com.javanc.repository.ChatRoomRepository;
import com.javanc.repository.UserRepository;
import com.javanc.repository.entity.ChatRoomEntity;
import com.javanc.repository.entity.UserEntity;
import com.javanc.service.ChatRoomService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    ChatRoomRepository chatRoomRepository;
    UserRepository userRepository;

    @Override
    public ChatRoomEntity createOrGetChatRoom() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String email = securityContext.getAuthentication().getName();
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTS)
        );

        UserEntity admin = userRepository.findById(2L).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTS)
        );

        return chatRoomRepository.findByUserAndAdmin(user, admin).orElseGet(
                () -> {
                    ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
                            .admin(admin)
                            .user(user)
                            .createdAt(LocalDateTime.now())
                            .build();
                    return chatRoomRepository.save(chatRoomEntity);
                }
        );
    }

    @Override
    public List<ChatRoomResponse> getChatRooms() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String email = securityContext.getAuthentication().getName();
        UserEntity admin = userRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTS)
        );

        List<ChatRoomEntity> chatRooms = chatRoomRepository.findAllByAdmin(admin);
        return chatRooms.stream().map(item -> ChatRoomResponse.builder()
                .chatRoomId(item.getId())
                .userId(item.getUser().getId())
                .name(item.getUser().getName())
                .avatar(item.getUser().getAvatar())
                .build()).collect(Collectors.toList());
    }
}
