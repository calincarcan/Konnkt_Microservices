package com.konnkt.backend.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PublicPostDto {
    String author;
    String title;
    String content;
}
