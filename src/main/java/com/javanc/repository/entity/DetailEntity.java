package com.javanc.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "stock")
    Long stock;
    @Column(name = "sold_count")
    Long sold_count;

    // size
    @ManyToOne
    @JoinColumn(name = "size_id")
    SizeEntity size;

    // color
    @ManyToOne
    @JoinColumn(name = "color_id")
    ColorEntity color;

    //product
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    ProductEntity product;


    @PostPersist
    protected void onCreate() {
        if(this.getSold_count() == null) this.setSold_count(0L);
    }
}
