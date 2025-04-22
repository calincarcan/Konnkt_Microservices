package com.konnkt.backend.repository;

import com.konnkt.backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // All comments of a post
//    List<Comment> findByPostId(Long postId);
    // All comments of a user
//    List<Comment> findByUserId(Long userId);
}