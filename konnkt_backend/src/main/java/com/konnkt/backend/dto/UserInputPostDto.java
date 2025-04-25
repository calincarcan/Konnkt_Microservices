package com.konnkt.backend.dto;

public record UserInputPostDto(
        String title,
        String content,
        Long forumId
) 
{
}
