package com.alura.forum.controllers;

import com.alura.forum.models.user.DataSignUpUser;
import com.alura.forum.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private static UserService userService;

    @PostMapping
    @Transactional
    public ResponseEntity signUp(@Valid @RequestBody DataSignUpUser dataSignUpUser){
        return ResponseEntity.ok(userService.signUp(dataSignUpUser));
    }
}
