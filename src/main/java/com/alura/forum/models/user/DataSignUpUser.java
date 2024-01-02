package com.alura.forum.models.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DataSignUpUser(
        @NotBlank
        String username,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password){
}
