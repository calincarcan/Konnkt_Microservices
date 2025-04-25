package com.konnkt.backend.dto;

public record CommentDtoDB(
        String content,
        String username,
        Long postId) {
}
