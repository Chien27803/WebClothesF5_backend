package com.javanc.repository.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="name")
    String name;
    @Column(name = "email")
    String email;
    @Column(name = "password")
    String password;
    @Column(name = "status",  columnDefinition = "tinyint(1)")
    Boolean status;
    @Column(name = "avatar", length = 512)
    String avatar;
    @Column(name = "phone")
    String phone;
    @Column(name = "deleted", columnDefinition = "tinyint(1)")
    Boolean deleted;
    // role
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(name = "user_role",
        joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false))
    @JsonManagedReference
    List<RoleEntity> roles = new ArrayList<>();

    // orders
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    List<OrderEntity> orders = new ArrayList<>();

    // address
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<UserAddressEntity> addresses = new ArrayList<>();


    // feedback
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<FeedbackEntity> feedbacks = new ArrayList<>();

    // shopping cart
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    ShoppingCartEntity shoppingCart;

    // payment
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<PaymentEntity> payments = new ArrayList<>();


    @OneToOne(mappedBy = "user" ,fetch = FetchType.LAZY)
    FavoriteEntity favorite;

}
