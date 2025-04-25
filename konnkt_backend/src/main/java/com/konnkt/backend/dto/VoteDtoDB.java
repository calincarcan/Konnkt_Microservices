package com.konnkt.backend.dto;

public record VoteDtoDB(
    String username,
    String parentType,
    Long parentId,
    int voteType
) {
    
}
