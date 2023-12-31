package com.alura.forum.services;

import com.alura.forum.models.user.AuthResponse;
import com.alura.forum.models.user.DataSignUpUser;
import com.alura.forum.models.user.User;
import com.alura.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    public AuthResponse signUp(DataSignUpUser dataSignUpUser) {
        User user = new User(dataSignUpUser);
        userRepository.save(user);
        return AuthResponse.builder()
                .token(tokenService.getToken(user))
                .build();
    }
}
