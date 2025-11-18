package com.javanc.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name="product_discount")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDiscountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "startTime")
    Date startTime;
    @Column(name = "endTime")
    Date endTime;

    // product
    @ManyToOne
    @JoinColumn(name = "product_id")
    ProductEntity product;

    // discount
    @ManyToOne
    @JoinColumn(name = "discount_id")
    DiscountEntity discount;
}
