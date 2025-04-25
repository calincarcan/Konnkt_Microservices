package com.konnkt.backend.dto;

// DTO sent to the DB
public record InputPostDtoDB(
        String title,
        String content,
        String username,
        Long forumId) 
{
}
