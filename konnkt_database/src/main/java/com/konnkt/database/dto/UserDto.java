package com.konnkt.database.dto;

public record UserDto(
    Long id,
    String username,
    String email
) {
}