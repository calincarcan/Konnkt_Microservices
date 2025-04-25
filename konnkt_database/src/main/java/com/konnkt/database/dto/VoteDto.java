package com.konnkt.database.dto;

public record VoteDto (
        Long id,
        Long authorId,
        String parentType, // post or comment
        Long parentId,
        int voteType
) {
}