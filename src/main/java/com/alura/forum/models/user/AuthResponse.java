package com.alura.forum.models.user;

public record AuthResponse(
        String token,
        Long id
) {
}
