package com.alura.forum.services;

import com.alura.forum.models.user.DataSignUpUser;
import com.alura.forum.models.user.User;
import com.alura.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private static UserRepository userRepository;
    public String signUp(DataSignUpUser dataSignUpUser) {
        User user = userRepository.save(new User(dataSignUpUser));
        return "User created successfully!";
    }
}
