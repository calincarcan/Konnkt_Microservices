package com.konnkt.backend.repository;

import com.konnkt.backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Find a role by its name
//    Optional<Role> findByRoleName(String roleName);
}