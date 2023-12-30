package com.alura.forum.models.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataUpdateResponse(
        @NotNull
        Long id,
        @NotBlank
        String text){
}
