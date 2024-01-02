package com.alura.forum.controllers;

import com.alura.forum.models.user.AuthResponse;
import com.alura.forum.models.user.DataLogInUser;
import com.alura.forum.models.user.DataSignUpUser;
import com.alura.forum.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
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
            tags = {"User->Authentication"},
            method = "POST",
            responses = {@ApiResponse(description = "Credentials matched", responseCode = "200"),
                    @ApiResponse(description = "Forbidden. The user doesn't have the permissions to get a properly response.", responseCode = "403")
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
            method = "POST",
            responses = {@ApiResponse(description = "User Created", responseCode = "200"), @ApiResponse(description = "Bad request (missing fields)", responseCode = "400"),
                    @ApiResponse(description = "Forbidden. The user doesn't have the permissions to get a properly response.", responseCode = "403")
            }
    )
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody DataSignUpUser dataSignUpUser) {
        return ResponseEntity.ok(authService.signUp(dataSignUpUser));
    }
}
