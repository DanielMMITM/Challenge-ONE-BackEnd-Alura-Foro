package com.alura.forum.services;

import static com.alura.forum.constants.Constants.USER_ID_NOT_FOUND;
import static com.alura.forum.constants.Constants.USER_DELETED_SUCCESSFULLY;

import com.alura.forum.models.user.User;
import com.alura.forum.models.user.UserInfo;
import com.alura.forum.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService{
    @Autowired
    private UserRepository userRepository;

    public UserInfo userDetails(Long id) {
        if (!userRepository.findById(id).isPresent()){
            throw new EntityNotFoundException(USER_ID_NOT_FOUND);
        }
        User user = userRepository.getReferenceById(id);
        return new UserInfo(user.getId(), user.getUsername(), user.getEmail());
    }

    public String deleteUser(Long id){
        if (!userRepository.findById(id).isPresent()){
            throw new EntityNotFoundException(USER_ID_NOT_FOUND);
        }
        User user = userRepository.getReferenceById(id);
        userRepository.delete(user);
        return USER_DELETED_SUCCESSFULLY;
    }
}
