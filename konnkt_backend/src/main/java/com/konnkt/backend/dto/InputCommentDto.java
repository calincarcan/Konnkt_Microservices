package com.konnkt.backend.dto;

public record InputCommentDto(
        String content,
        Long postId
) {
}
