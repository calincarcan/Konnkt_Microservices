package com.konnkt.database.service;

import com.konnkt.database.entity.Post;
import java.util.List;

public interface PostServiceInterface {
    List<Post> findAll();
    List<Post> findByForumId(Long forumId);
    Post findById(Long id);
    Post create(Post post);
    Post update(Long id, Post post);
    void delete(Long id);
}