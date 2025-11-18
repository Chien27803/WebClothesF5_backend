    package com.javanc.repository.entity;

    import jakarta.persistence.*;
    import lombok.*;
    import lombok.experimental.FieldDefaults;

    @Entity
    @Table(name = "order_product")
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Builder
    public class OrderProductEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        @ManyToOne
        @JoinColumn(name = "product_id", nullable = false)
        ProductEntity product;
        @ManyToOne
        @JoinColumn(name = "order_id", nullable = false)
        OrderEntity order;
        @Column(name = "quantity")
        Long quantity;
        @Column(name = "price")
        Float price;

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
