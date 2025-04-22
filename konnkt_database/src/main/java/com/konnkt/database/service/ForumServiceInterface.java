package com.konnkt.database.service;

import com.konnkt.database.entity.Forum;
import java.util.List;

public interface ForumServiceInterface {
    List<Forum> findAll();
    Forum findById(Long id);
    Forum create(Forum forum);
    Forum update(Long id, Forum forum);
    void delete(Long id);
}