package com.konnkt.database.service;

import com.konnkt.database.dto.*;
import com.konnkt.database.entity.*;
import java.util.*;

public interface CommentServiceInterface {
    List<CommentDto> findAll();
    Optional<CommentDto> findById(Long id);
    Optional<CommentDto> createComment(InputCommentDto commentDto);
    Optional<CommentDto> updateComment(Long id, String content);
    Boolean deleteComment(Long id);
}