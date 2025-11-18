package com.javanc.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "image")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "src")
    String src;
    @Column(name = "color_id")
    Long color_id;
    // product
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    ProductEntity product;

    @OneToMany(mappedBy = "image", fetch = FetchType.LAZY)
    List<ProductShoppingCartEntity> productShoppingCarts = new ArrayList<>();

    @OneToMany(mappedBy = "image", fetch = FetchType.LAZY)
    List<OrderProductEntity> orderProducts = new ArrayList<>();

}
