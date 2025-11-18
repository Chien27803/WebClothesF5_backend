package com.javanc.repository;


import com.javanc.repository.entity.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountRepository extends JpaRepository<DiscountEntity, Long> {

}
