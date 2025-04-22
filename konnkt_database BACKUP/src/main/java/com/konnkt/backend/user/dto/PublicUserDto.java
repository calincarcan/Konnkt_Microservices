package com.konnkt.backend.user.dto;

import lombok.Getter;

@Getter
public class PublicUserDto {
    private final String username;
    private final String email;
    private final String role;

    public PublicUserDto(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
