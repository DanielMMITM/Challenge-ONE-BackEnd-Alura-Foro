package com.alura.forum.models.user;

import lombok.Builder;

@Builder
public record AuthResponse(String token) {
}
