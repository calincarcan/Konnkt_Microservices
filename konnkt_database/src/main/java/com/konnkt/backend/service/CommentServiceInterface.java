package com.konnkt.backend.service;

import com.konnkt.backend.entity.Comment;
import java.util.List;

public interface CommentServiceInterface {
    List<Comment> findAll();
    List<Comment> findByPostId(Long postId);
    Comment findById(Long id);
    Comment create(Comment comment);
    Comment update(Long id, Comment comment);
    void delete(Long id);
}