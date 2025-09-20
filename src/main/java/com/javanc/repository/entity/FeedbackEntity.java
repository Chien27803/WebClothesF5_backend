package com.javanc.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "feedback")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "description")
    String description;

    @Column(name = "rating")
    float rating;

    @Column(name = "feedback_time")
    Date time;

    @Column(name = "deleted", columnDefinition = "tinyint(1)")
    Boolean deleted;

    // user
    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    // product
    @ManyToOne
    @JoinColumn(name = "product_id")
    ProductEntity product;
}
