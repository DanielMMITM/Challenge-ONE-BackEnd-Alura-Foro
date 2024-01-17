package com.alura.forum.controllers;

import com.alura.forum.infra.errors.ErrorResponse;
import com.alura.forum.models.user.UserInfo;
import com.alura.forum.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearer-key")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve the information of the current user from the database",
            description = "The user id is send through the URL and then it shows the user information.",
            tags = {"User"},
            parameters = {
                @Parameter(name = "id",
                        description = "Id of the user to show its information",
                        required = true,
                        example = "1",
                        schema = @Schema(type = "Long")),
            },
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(description = "User information retrieved", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = UserInfo.class))
            ),
            @ApiResponse(description = "Unauthorized. You must authenticate", responseCode = "401",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(description = "You don't have permission", responseCode = "403",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )

    })
    public ResponseEntity<UserInfo> userDetails(@PathVariable Long id){
        return ResponseEntity.ok(userService.userDetails(id));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete the user from the database",
            description = "The user id is send through the URL.",
            tags = {"User"},
            parameters = {
                @Parameter(name = "id",
                        description = "Id of the user to delete",
                        required = true,
                        example = "1",
                        schema = @Schema(type = "Long")),
            },
            method = "DELETE"
    )
    @ApiResponses(value = {
            @ApiResponse(description = "User deleted. It returns a string value to report that it was successfully deleted",
                    responseCode = "200",
                    content = @Content(schema = @Schema(type = "String"))
            ),
            @ApiResponse(description = "Unauthorized. You must authenticate",
                    responseCode = "401",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(description = "You don't have permission", responseCode = "403",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    public ResponseEntity deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
