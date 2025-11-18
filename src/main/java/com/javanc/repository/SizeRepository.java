package com.javanc.repository;

import com.javanc.repository.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SizeRepository extends JpaRepository<SizeEntity, Long> {
    Optional<SizeEntity> findById(Long id);
}
