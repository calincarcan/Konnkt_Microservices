package com.konnkt.backend.repository;

import com.konnkt.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsUserByEmail(String email);
    Boolean existsUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
    List<User> findAll();
    Optional<User> findById(Long id);

}
