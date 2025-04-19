package com.konnkt.backend.vote.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PublicVoteDto {
    int voteType;
    Long postId;
    Long commentId;
    String author;
}
