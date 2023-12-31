package com.alura.forum.services;

import com.alura.forum.models.user.DataSignUpUser;
import com.alura.forum.models.user.User;
import com.alura.forum.models.user.UserDetails;
import com.alura.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String signUp(DataSignUpUser dataSignUpUser) {
        User user = new User(dataSignUpUser);
        userRepository.save(user);
        return "User created successfully!";
    }

    public UserDetails userDetails(Long id) {
        User user = userRepository.getReferenceById(id);
        return new UserDetails(user.getId(), user.getName(), user.getEmail());
    }

    public String deleteUser(Long id){
        User user = userRepository.getReferenceById(id);
        userRepository.delete(user);
        return "User deleted successfully!";
    }

}
