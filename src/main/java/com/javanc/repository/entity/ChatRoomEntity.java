package com.javanc.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_room")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity user;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    UserEntity admin;

    @Column(name = "created_at")
    LocalDateTime createdAt;

}
