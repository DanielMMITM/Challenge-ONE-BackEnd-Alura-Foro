package com.alura.forum.controllers;

import com.alura.forum.models.user.AuthResponse;
import com.alura.forum.models.user.DataLogInUser;
import com.alura.forum.models.user.DataSignUpUser;
import com.alura.forum.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;

    @PostMapping(value = "login")
    @Operation(summary = "Perform a login action to get access to the private endpoints",
            description = "The credentials of the user are send through the body.",
            tags = {"Authentication"},
            parameters = {
                @Parameter(name = "username",
                        required = true,
                        example = "user123",
                        schema = @Schema(type = "String")
                ),
                @Parameter(name = "password",
                        required = true,
                        example = "123456",
                        schema = @Schema(type = "String")
                ),
            },
            method = "POST",
            responses = {@ApiResponse(description = "Credentials matched", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized. You must authenticate",
                            responseCode = "401")
            }
    )
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid DataLogInUser dataLogInUser){
        return ResponseEntity.ok(authService.login(dataLogInUser));
    }

    @PostMapping(value = "signup")
    @Transactional
    @Operation(summary = "Perform a register action. A new user is created in the database",
            description = "The user info are send through the body so a insert query is performed into the database.",
            tags = {"User"},
            parameters = {
                @Parameter(name = "username",
                        required = true,
                        example = "user123",
                        schema = @Schema(type = "String")
                ),
                @Parameter(name = "email",
                        required = true,
                        example = "user123@gmail.com",
                        schema = @Schema(type = "String")
                ),
                @Parameter(name = "password",
                        required = true,
                        example = "123456",
                        schema = @Schema(type = "String")
                ),
            },
            method = "POST",
            responses = {@ApiResponse(description = "User Created", responseCode = "200"),
                    @ApiResponse(description = "Bad request (missing fields)", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized. You must authenticate",
                            responseCode = "401")
            }
    )
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody DataSignUpUser dataSignUpUser) {
        return ResponseEntity.ok(authService.signUp(dataSignUpUser));
    }
}
