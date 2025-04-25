package com.konnkt.backend.dto;

public record InputPostDto(
        String title,
        String content,
        Long forumId
) 
{
}
