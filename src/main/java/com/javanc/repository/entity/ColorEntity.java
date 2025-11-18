package com.javanc.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="color")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ColorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "hex_code")
    String hexCode;
    //detail
    @OneToMany(mappedBy = "color", fetch = FetchType.LAZY)
    @JsonIgnore
    List<DetailEntity> details = new ArrayList<>();

    @OneToMany(mappedBy = "color", fetch = FetchType.LAZY)
    List<ProductShoppingCartEntity> productShoppingCarts = new ArrayList<>();

    @OneToMany(mappedBy = "color", fetch = FetchType.LAZY)
    List<OrderProductEntity> orderProducts = new ArrayList<>();
}
