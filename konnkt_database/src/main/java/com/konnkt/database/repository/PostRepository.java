package com.konnkt.database.repository;

import com.konnkt.database.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // All posts of a forum
//    List<Post> findByForumId(Long forumId);
    // All posts of a user
//    List<Post> findByUserId(Long userId);
}