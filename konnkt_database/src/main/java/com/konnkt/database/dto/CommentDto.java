package com.konnkt.database.dto;

public record CommentDto (
        Long id,
        String content,
        Long authorId,
        Long postId,
        int score
) {
}
