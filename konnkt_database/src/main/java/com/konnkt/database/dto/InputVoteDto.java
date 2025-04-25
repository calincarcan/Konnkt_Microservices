package com.konnkt.database.dto;

public record InputVoteDto (
        String username,
        String parentType, // post or comment
        Long parentId,
        int voteType
) {
}