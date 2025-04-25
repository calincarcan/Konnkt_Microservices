package com.konnkt.backend.dto;

public record InputPostDto(
                String title,
                String content,
                String username,
                Long forumId) {
}
