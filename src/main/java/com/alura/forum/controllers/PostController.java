package com.alura.forum.controllers;

import com.alura.forum.models.post.DataPost;
import com.alura.forum.models.post.DataResponsePost;
import com.alura.forum.services.PostService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.crypto.Data;
import java.net.URI;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping
    public ResponseEntity<DataResponsePost> publish(@Valid @RequestBody DataPost dataPost, UriComponentsBuilder uriComponentsBuilder){
        return new ResponseEntity(postService.publish(dataPost, uriComponentsBuilder), HttpStatus.CREATED);
    }

}