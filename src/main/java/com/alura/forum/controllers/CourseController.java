package com.alura.forum.controllers;

import com.alura.forum.infra.errors.ErrorResponse;
import com.alura.forum.models.course.Course;
import com.alura.forum.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courses")
@SecurityRequirement(name = "bearer-key")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    @Operation(summary = "Retrieve all the courses from the database",
            description = "This endpoint retrieves all the courses from the database to show them to the user. ",
            tags = {"Courses"},
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Courses retrieved", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = List.class))
            ),
            @ApiResponse(description = "Unauthorized. You must authenticate", responseCode = "401",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(description = "You don't have permission", responseCode = "403",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity<List<Course>> listCourses(){
        return ResponseEntity.ok(courseService.listCourses());
    }
}
