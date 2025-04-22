package com.konnkt.backend.repository;

import com.konnkt.backend.entity.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {
    // Method to find a forum by its name
    boolean existsByName(String name);
}