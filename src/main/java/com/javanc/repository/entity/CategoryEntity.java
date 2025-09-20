package com.javanc.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "deleted", columnDefinition = "tinyint(1)")
    Boolean deleted;
    @Column(name = "parent_id")
    Long parentId;
    // product
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonIgnore
    List<ProductEntity> products = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.deleted = false;
        if (this.parentId == null) this.parentId = 0L;
    }
}
