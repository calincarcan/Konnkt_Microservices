package com.konnkt.database.repository;

import com.konnkt.database.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    // All votes of a post
//    Optional<Vote> findByUserIdAndPostId(Long userId, Long postId);
    // All votes of a comment
//    Optional<Vote> findByUserIdAndCommentId(Long userId, Long commentId);
}