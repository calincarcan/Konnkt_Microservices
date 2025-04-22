package com.konnkt.backend.service;

import com.konnkt.backend.entity.Forum;
import java.util.List;

public interface ForumServiceInterface {
    List<Forum> findAll();
    Forum findById(Long id);
    Forum create(Forum forum);
    Forum update(Long id, Forum forum);
    void delete(Long id);
}