package com.alura.forum.models.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataUpdatePost(
        @NotNull
        Long id,
        @NotBlank
        String title,
        @NotBlank
        String text,
        StatusPost statusPost,
        @NotNull
        Long courseId){
}
