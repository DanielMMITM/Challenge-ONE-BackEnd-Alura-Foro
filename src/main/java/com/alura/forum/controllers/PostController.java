package com.alura.forum.controllers;

import com.alura.forum.models.post.DataListPosts;
import com.alura.forum.models.post.DataUpdatePost;
import com.alura.forum.models.post.DataResponsePost;
import com.alura.forum.models.post.DataPost;
import com.alura.forum.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
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
            description = "It requires a title, text, course id and the user id that is the one that its posting it to" +
                    "successfully insert the post in the database.",
            parameters = {
                @Parameter(name = "Title",
                    description = "Title of the post",
                    required = true,
                    example = "I need help with my login, can't handle this error: Error MB676",
                    schema = @Schema(type = "String")),
                @Parameter(name = "Text",
                        description = "Text/body of the post",
                        required = true,
                        example = "I was doing my model and tried to run the app then this error pop up",
                        schema = @Schema(type = "String")),
                @Parameter(name = "UserId",
                        description = "Id of the user that is posting",
                        required = true,
                        example = "1",
                        schema = @Schema(type = "Long")),
                @Parameter(name = "CourseId",
                        description = "Id of the course that your post is about",
                        required = true,
                        example = "1",
                        schema = @Schema(type = "Long")),
            },
            tags = {"Posts"},
            method = "POST",
            responses = {@ApiResponse(description = "Post created", responseCode = "201"),
                    @ApiResponse(description = "Bad request (missing fields)", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized. You must authenticate",
                            responseCode = "401")
            }
    )
    public ResponseEntity<DataResponsePost> publish(@Valid @RequestBody DataPost dataPost, UriComponentsBuilder uriComponentsBuilder){
        return postService.publish(dataPost, uriComponentsBuilder);
    }

    @GetMapping
    @Operation(summary = "Retrieve all the posts from the database",
            description = "This endpoint retrieves all the posts from the database to show them to the user. " +
                    "If required send the page, size or sort by the URL.",
            tags = {"Posts"},
            parameters = {
                @Parameter(name = "Page",
                        description = "Number of page to show",
                        example = "1",
                        schema = @Schema(type = "Int")),
                @Parameter(name = "Size",
                        description = "Number of objects to be shown on the page",
                        example = "1",
                        schema = @Schema(type = "Int")),
                @Parameter(name = "Sort",
                        description = "Value to sort the objects",
                        example = "title",
                        schema = @Schema(type = "String"))
            },
            method = "GET",
            responses = {@ApiResponse(description = "Posts retrieved", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized. You must authenticate",
                            responseCode = "401")
            }
    )
    public ResponseEntity<Page<DataListPosts>> listPosts(@PageableDefault(size = 2) Pageable pagination){
        return ResponseEntity.ok(postService.listPosts(pagination));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a specific post from the database",
            description = "This endpoint retrieves the post selected by the user and shows it with all the answers." +
                    "The post id is send through the URL.",
            parameters = {
                @Parameter(name = "Post Id",
                        in = ParameterIn.PATH,
                        description = "Id of the post to show",
                        required = true,
                        example = "1",
                        schema = @Schema(type = "Long")),
            },
            tags = {"Posts"},
            method = "GET",
            responses = {@ApiResponse(description = "Post retrieved", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized. You must authenticate",
                            responseCode = "401")
            }
    )
    public ResponseEntity<DataResponsePost>viewPost(@PathVariable Long id){
        return new ResponseEntity(postService.viewPost(id), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete a specific post from the database",
            description = "This endpoint delete the post selected by the user. The post id is send through the URL.",
            parameters = {
                @Parameter(name = "Post id",
                        in = ParameterIn.PATH,
                        description = "Id of the post to delete",
                        required = true,
                        example = "1",
                        schema = @Schema(type = "Long")),
            },
            tags = {"Posts"},
            method = "DELETE",
            responses = {@ApiResponse(description = "Post deleted", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized. You must authenticate",
                            responseCode = "401")
            }
    )
    public ResponseEntity deletePost(@PathVariable Long id){
        return ResponseEntity.ok(postService.deletePost(id));
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Edit a specific post from the database",
            description = "This endpoint update the post selected by the user. The body requires its id, title, text, status and courseId.",
            parameters = {
                @Parameter(name = "Post id",
                        description = "Id of the post that you want to edit",
                        required = true,
                        example = "1",
                        schema = @Schema(type = "Long")),
                @Parameter(name = "Title",
                        description = "Title of the post",
                        required = true,
                        example = "I need help with my login, can't handle this error: Error MB676",
                        schema = @Schema(type = "String")),
                @Parameter(name = "Text",
                        description = "Text/body of the post",
                        required = true,
                        example = "I was doing my model and tried to run the app then this error pop up",
                        schema = @Schema(type = "String")),
                @Parameter(name = "StatusPost",
                        description = "Status of the post",
                        example = "NOT_SOLVED",
                        schema = @Schema(type = "statusPost")),
                @Parameter(name = "Course Id",
                        description = "Id of the course that your post is about",
                        required = true,
                        example = "1",
                        schema = @Schema(type = "Long")),
            },
            tags = {"Posts"},
            method = "PUT",
            responses = {@ApiResponse(description = "Post updated", responseCode = "200"),
                    @ApiResponse(description = "Bad request (missing fields)", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized. You must authenticate",
                            responseCode = "401")
            }
    )
    public ResponseEntity<DataResponsePost> updatePost(@RequestBody @Valid DataUpdatePost dataUpdatePost){
        return ResponseEntity.ok(postService.updatePost(dataUpdatePost));
    }
}