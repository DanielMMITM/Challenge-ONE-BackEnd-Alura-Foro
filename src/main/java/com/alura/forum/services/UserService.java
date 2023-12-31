package com.alura.forum.services;

import com.alura.forum.models.user.AuthResponse;
import com.alura.forum.models.user.DataSignUpUser;
import com.alura.forum.models.user.User;
import com.alura.forum.models.user.UserDetails;
import com.alura.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public static final String DELETED_SUCCESSFULLY = "User deleted successfully!";
    @Autowired
    private UserRepository userRepository;


    public UserDetails userDetails(Long id) {
        User user = userRepository.getReferenceById(id);
        return new UserDetails(user.getId(), user.getName(), user.getEmail());
    }

    public String deleteUser(Long id){
        User user = userRepository.getReferenceById(id);
        userRepository.delete(user);
        return DELETED_SUCCESSFULLY;
    }

}
