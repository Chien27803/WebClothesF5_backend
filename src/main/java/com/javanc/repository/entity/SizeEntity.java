package com.javanc.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="size")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SizeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;

    // detail
    @OneToMany(mappedBy = "size", fetch = FetchType.LAZY)
    @JsonIgnore
    List<DetailEntity> details = new ArrayList<>();

    @OneToMany(mappedBy = "size", fetch = FetchType.LAZY)
    List<ProductShoppingCartEntity> productShoppingCarts = new ArrayList<>();

    @OneToMany(mappedBy = "size", fetch = FetchType.LAZY)
    List<OrderProductEntity> orderProducts = new ArrayList<>();
}
