package com.javanc.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "time")
    Date time;
    @Column(name = "status")
    String status;
    @Column(name = "deleted", columnDefinition = "tinyint(1)")
    Boolean deleted;
    @Column(name = "total")
    float total;
    // user
    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;
    // order_product
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderProductEntity> orderProducts = new ArrayList<>();

    // address
    @OneToOne
    @JoinColumn(name = "address_id", unique = true, nullable = false)
    AddressEntity address;

    // payment
    @ManyToOne
    @JoinColumn(name = "payment_id")
    PaymentEntity payment;
}
