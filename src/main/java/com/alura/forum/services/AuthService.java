package com.alura.forum.services;

import static com.alura.forum.constants.Constants.USER_NOT_FOUND;

import com.alura.forum.models.role.Role;
import com.alura.forum.models.user.*;
import com.alura.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class AuthService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse login(DataLogInUser dataLogInUser) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dataLogInUser.username(), dataLogInUser.password()));
        UserDetails user = userRepository.findOneByUsername(dataLogInUser.username()).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
        String token = tokenService.getToken(user);
        return new AuthResponse(token);
    }

    public UserInfo signUp(DataSignUpUser dataSignUpUser) {
        User user = User.builder()
                .username(dataSignUpUser.username())
                .email(dataSignUpUser.email())
                .password(passwordEncoder.encode(dataSignUpUser.password()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return new UserInfo(user.getId(), user.getEmail(), user.getUsername());
    }
}
