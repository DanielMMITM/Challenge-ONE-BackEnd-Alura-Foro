package com.alura.forum.services;

import com.alura.forum.models.user.*;
import com.alura.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;


@Service
public class AuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse login(DataLogInUser dataLogInUser) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dataLogInUser.username(), dataLogInUser.password()));
        UserDetails user = userRepository.findByUsername(dataLogInUser.username()).orElseThrow();
        String token = tokenService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse signUp(DataSignUpUser dataSignUpUser) {
        User user = new User(dataSignUpUser);
        userRepository.save(user);
        return AuthResponse.builder()
                .token(tokenService.getToken(user))
                .build();
    }
}
