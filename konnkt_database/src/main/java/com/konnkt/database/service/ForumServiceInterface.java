package com.konnkt.database.service;

import com.konnkt.database.dto.*;
import com.konnkt.database.entity.*;
import java.util.*;

public interface ForumServiceInterface {
    List<ForumDto> findAll();
    Optional<ForumDto> findById(Long id);
    Optional<ForumDto> findByName(String name);
    Optional<ForumDto> createForum(InputForumDto forumDto);
    Optional<ForumDto> updateForumName(Long id, String name);
    Optional<ForumDto> updateForumDescription(Long id, String description);
    Boolean deleteForumById(Long id);
    Boolean deleteForumByName(String name);
}