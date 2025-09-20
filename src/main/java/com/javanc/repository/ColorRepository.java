package com.javanc.repository;


import com.javanc.repository.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ColorRepository extends JpaRepository<ColorEntity, Long> {
    Optional<ColorEntity> findById(Long id);
    List<ColorEntity> findAllByIdIn(List<Long> ids);
}
