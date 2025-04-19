package com.konnkt.backend.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NewPostDto {
    String title;
    String content;
    String forum;
}
