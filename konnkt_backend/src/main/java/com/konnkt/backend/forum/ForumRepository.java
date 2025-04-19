package com.konnkt.backend.forum;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface ForumRepository extends JpaRepository<Forum, Long> {
    Optional<Forum> findByName(String name);
}
