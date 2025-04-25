package com.konnkt.database.dto;

public record InputCommentDto (
        String content,
        String username,
        Long postId
) {
}
