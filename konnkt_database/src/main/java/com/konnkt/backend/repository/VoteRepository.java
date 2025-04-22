package com.konnkt.backend.repository;

import com.konnkt.backend.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    // All votes of a post
//    Optional<Vote> findByUserIdAndPostId(Long userId, Long postId);
    // All votes of a comment
//    Optional<Vote> findByUserIdAndCommentId(Long userId, Long commentId);
}