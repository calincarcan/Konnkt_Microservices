package com.konnkt.database.service;

import com.konnkt.database.dto.*;
import java.util.*;

public interface PostServiceInterface {
    List<PostDto> findAll();
    Optional<PostDto> findById(Long id);
    Optional<PostDto> createPost(InputPostDto inputPostDto);
    Optional<PostDto> updatePostTitle(Long id, String title);
    Optional<PostDto> updatePostContent(Long id, String content);
    Boolean deletePost(Long id);
}