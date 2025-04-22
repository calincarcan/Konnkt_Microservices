package com.konnkt.backend.repository;

import com.konnkt.backend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // All posts of a forum
//    List<Post> findByForumId(Long forumId);
    // All posts of a user
//    List<Post> findByUserId(Long userId);
}