package com.konnkt.backend.dto;

public record InputVoteDto(
        String parentType,
        Long parentId,
        int voteType) {

}
