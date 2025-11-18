package com.javanc.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "discount")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "percent")
    Double percent;
    @Column(name = "deleted", columnDefinition = "tinyint(1)")
    Boolean deleted;

    // product_discount
    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY)
    @JsonIgnore
    List<ProductDiscountEntity> productDiscounts = new ArrayList<>();
}
