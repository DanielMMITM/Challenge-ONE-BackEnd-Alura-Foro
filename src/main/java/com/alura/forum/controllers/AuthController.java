package com.alura.forum.controllers;

import com.alura.forum.models.user.AuthResponse;
import com.alura.forum.models.user.DataLogInUser;
import com.alura.forum.models.user.DataSignUpUser;
import com.alura.forum.models.user.User;
import com.alura.forum.repositories.UserRepository;
import com.alura.forum.services.AuthService;
import com.alura.forum.services.TokenService;
import com.alura.forum.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid DataLogInUser dataLogInUser){
        return ResponseEntity.ok(authService.login(dataLogInUser));
    }

    @PostMapping(value = "signup")
    @Transactional
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody DataSignUpUser dataSignUpUser) {
        return ResponseEntity.ok(authService.signUp(dataSignUpUser));
    }
}
