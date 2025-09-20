package com.javanc.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javanc.enums.SizeEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;
    @Column(name = "slug")
    String slug;
    @Column(name = "price")
    float price;
    @Column(name = "quantity")
    Long quantity;
    @Column(name = "deleted")
    Boolean deleted;

    // order_product
    @OneToMany(mappedBy="product", fetch = FetchType.LAZY)
    @JsonIgnore
    List<OrderProductEntity> orderProducts = new ArrayList<>();

    // feedback
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnore
    List<FeedbackEntity> feedbacks = new ArrayList<>();

    // product_shoppingcart
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnore
    List<ProductShoppingCartEntity> productShoppingCarts = new ArrayList<>();

    // category
    @ManyToOne
    @JoinColumn(name = "category_id")
    CategoryEntity category;

    // image
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE}, orphanRemoval = true)
    List<ImageEntity> images = new ArrayList<>();

    // product_discount
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    List<ProductDiscountEntity> productDiscounts = new ArrayList<>();

    // detail
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE}, orphanRemoval = true)
    List<DetailEntity> details = new ArrayList<>();

    @PostPersist
    protected void onCreate() {
        if(this.getDeleted() == null) this.setDeleted(false);
    }
}
