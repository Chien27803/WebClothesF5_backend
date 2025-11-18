package com.javanc.repository;

import com.javanc.repository.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByCode(String code);
    List<RoleEntity> findAll();
    Optional<RoleEntity> findById(Long id);
}
