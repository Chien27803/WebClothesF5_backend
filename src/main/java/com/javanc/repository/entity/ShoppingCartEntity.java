package com.javanc.repository.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shoppingcart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingCartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    // user
    @OneToOne
    @JsonBackReference("user-shoppingCart")
    @JoinColumn(name = "user_id")
    UserEntity user;


    // product_shoppingcart
    @OneToMany(mappedBy = "shoppingCart", fetch = FetchType.LAZY)
    List<ProductShoppingCartEntity> productShoppingCarts = new ArrayList<>();

}
