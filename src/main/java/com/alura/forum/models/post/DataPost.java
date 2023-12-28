package com.alura.forum.models.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DataPost(

        @NotBlank
        String title,

        @NotBlank
        String text,

        @NotNull
        Long user_id,

        @NotNull
        Long course_id){
}
