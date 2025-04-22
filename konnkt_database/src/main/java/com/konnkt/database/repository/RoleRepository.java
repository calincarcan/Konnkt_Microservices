package com.konnkt.database.repository;

import com.konnkt.database.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Find a role by its name
//    Optional<Role> findByRoleName(String roleName);
}