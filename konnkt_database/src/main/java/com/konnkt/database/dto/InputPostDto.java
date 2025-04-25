package com.konnkt.database.dto;

public record InputPostDto(
        String title,
        String content,
        String username,
        Long forumId
) {
}
