package com.javanc.repository.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "content")
    String content;
    @Column(name = "status",  columnDefinition = "tinyint(1)")
    Boolean status;
    @Column(name = "createdAt")
    LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;
}
