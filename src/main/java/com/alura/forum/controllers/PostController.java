package com.alura.forum.controllers;

import com.alura.forum.models.post.DataListPosts;
import com.alura.forum.models.post.DataUpdatePost;
import com.alura.forum.models.post.DataResponsePost;
import com.alura.forum.models.post.DataPost;
import com.alura.forum.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearer-key")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    @Transactional
    @Operation(summary = "Create a new post in the database",
            description = "It requires a title, text, course id and the user id that is the one that its posting it to successfully insert the post in the database.",
            tags = {"user->posts"},
            method = "POST",
            responses = {@ApiResponse(description = "Post created", responseCode = "201"), @ApiResponse(description = "Bad request (missing fields)", responseCode = "400"),
                    @ApiResponse(description = "Forbidden. The user doesn't have the permissions to get a properly response.", responseCode = "403")
            }
    )
    public ResponseEntity<DataResponsePost> publish(@Valid @RequestBody DataPost dataPost, UriComponentsBuilder uriComponentsBuilder){
        return postService.publish(dataPost, uriComponentsBuilder);
    }

    @GetMapping
    @Operation(summary = "Retrieve all the posts from the database",
            description = "This endpoint retrieves all the posts from the database to show them to the user. If required send the page, size or sort by the URL.",
            tags = {"user->posts"},
            method = "GET",
            responses = {@ApiResponse(description = "Posts retrieved", responseCode = "200"),
                    @ApiResponse(description = "Forbidden. The user doesn't have the permissions to get a properly response.", responseCode = "403")
            }
    )
    public ResponseEntity<Page<DataListPosts>> listPosts(@PageableDefault(size = 2) Pageable pagination){
        return ResponseEntity.ok(postService.listPosts(pagination));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a specific post from the database",
            description = "This endpoint retrieves the post selected by the user and shows it with all the answers. The post id is send through the URL.",
            tags = {"user->posts"},
            method = "GET",
            responses = {@ApiResponse(description = "Post retrieved", responseCode = "200"),
                    @ApiResponse(description = "Forbidden. The user doesn't have the permissions to get a properly response.", responseCode = "403")
            }
    )
    public ResponseEntity<DataResponsePost>viewPost(@PathVariable Long id){
        return new ResponseEntity(postService.viewPost(id), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete a specific post from the database",
            description = "This endpoint delete the post selected by the user. The post id is send through the URL.",
            tags = {"user->posts"},
            method = "DELETE",
            responses = {@ApiResponse(description = "Post deleted", responseCode = "200"),
                    @ApiResponse(description = "Forbidden. The user doesn't have the permissions to get a properly response.", responseCode = "403")
            }
    )
    public ResponseEntity deletePost(@PathVariable Long id){
        return ResponseEntity.ok(postService.deletePost(id));
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Edit a specific post from the database",
            description = "This endpoint update the post selected by the user. The body requires its id, title, text, status and course_id.",
            tags = {"user->posts"},
            method = "PUT",
            responses = {@ApiResponse(description = "Post updated", responseCode = "200"),
                    @ApiResponse(description = "Forbidden. The user doesn't have the permissions to get a properly response.", responseCode = "403")
            }
    )
    public ResponseEntity<DataResponsePost> updatePost(@RequestBody @Valid DataUpdatePost dataUpdatePost){
        return ResponseEntity.ok(postService.updatePost(dataUpdatePost));
    }
}