package com.javanc.repository;

import com.javanc.repository.entity.UserAddressEntity;
import com.javanc.repository.entity.UserEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddressEntity, Long> {

    List<UserAddressEntity> findByUser(UserEntity user);
    Optional<UserAddressEntity> findByUserAndAddress_id(UserEntity user, Long id);
    List<UserAddressEntity> findAllByUser(UserEntity user);

}
