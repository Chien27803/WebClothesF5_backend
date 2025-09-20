package com.javanc.repository.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "favorite")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FavoriteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    // user
    @OneToOne
    @JsonBackReference("user-favorite")
    @JoinColumn(name = "user_id")
    UserEntity user;


    // product_shoppingcart
    @OneToMany(mappedBy = "favorite", fetch = FetchType.LAZY)
    List<ProductFavoriteEntity> productFavoriteEntities = new ArrayList<>();

}
