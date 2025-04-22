package com.konnkt.backend.service;

import com.konnkt.backend.entity.Post;
import java.util.List;

public interface PostServiceInterface {
    List<Post> findAll();
    List<Post> findByForumId(Long forumId);
    Post findById(Long id);
    Post create(Post post);
    Post update(Long id, Post post);
    void delete(Long id);
}