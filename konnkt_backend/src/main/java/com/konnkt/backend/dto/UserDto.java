package com.konnkt.backend.dto;

public record UserDto(
    Long id,
    String username,
    String email
) {
}