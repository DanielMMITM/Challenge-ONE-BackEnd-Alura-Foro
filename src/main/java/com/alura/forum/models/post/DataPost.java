package com.alura.forum.models.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataPost(
        @NotBlank
        String title,
        @NotBlank
        String text,
        @NotNull
        Long userId,
        @NotNull
        Long courseId){
}
