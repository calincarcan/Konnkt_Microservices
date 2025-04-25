package com.konnkt.backend.dto;

public record PostDto (
        Long id,
        String title,
        String content,
        Long authorId,
        Long forumId,
        int score
) {

}
