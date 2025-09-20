package com.javanc.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="product_shoppingcart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductShoppingCartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "quantity")
    Long quantity;
    @Column(name = "stock")
    Long stock;

    // product
    @ManyToOne
    @JoinColumn(name = "product_id")
    ProductEntity product;

    // shoppingcart
    @ManyToOne
    @JoinColumn(name = "shoppingcart_id")
    ShoppingCartEntity shoppingCart;

    @ManyToOne
    @JoinColumn(name = "color_id")
    ColorEntity color;

    @ManyToOne
    @JoinColumn(name = "size_id")
    SizeEntity size;

    @ManyToOne
    @JoinColumn(name = "image_id")
    ImageEntity image;

}
