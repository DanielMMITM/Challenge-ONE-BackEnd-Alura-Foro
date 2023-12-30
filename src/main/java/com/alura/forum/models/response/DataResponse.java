package com.alura.forum.models.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataResponse(
        @NotBlank
        String text,
        @NotNull
        Long post_id,
        @NotNull
        Long user_id
) {
}
