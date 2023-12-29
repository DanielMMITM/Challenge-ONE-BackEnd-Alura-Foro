package com.alura.forum.controllers;

import com.alura.forum.models.post.*;
import com.alura.forum.services.PostService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping
    @Transactional
    public ResponseEntity<DataResponsePost> publish(@Valid @RequestBody DataPost dataPost, UriComponentsBuilder uriComponentsBuilder){
        return new ResponseEntity(postService.publish(dataPost, uriComponentsBuilder), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<DataListPosts>> listPosts(@PageableDefault(size = 2) Pageable paginacion){
        return new ResponseEntity(postService.listPosts(paginacion), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponsePost>viewPost(@PathVariable Long id){
        return new ResponseEntity(postService.viewPost(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletePost(@PathVariable Long id){
        return ResponseEntity.ok(postService.deletePost(id));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DataResponsePost> updatePost(@RequestBody @Valid DataUpdatePost dataUpdatePost){
        return ResponseEntity.ok(postService.updatePost(dataUpdatePost));
    }


}