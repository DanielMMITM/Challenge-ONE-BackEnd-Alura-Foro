package com.alura.forum.controllers;

import com.alura.forum.models.response.DataResponse;
import com.alura.forum.models.response.DataResponseBody;
import com.alura.forum.models.response.DataUpdateResponse;
import com.alura.forum.services.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/responses")
@SecurityRequirement(name = "bearer-key")
public class ResponseController {
    @Autowired
    private ResponseService responseService;

    @PostMapping
    @Transactional
    @Operation(summary = "Creates a new answer/comment/response inside the post",
            description = "The user sends the information through the body and includes the post id that is watching and also its own id," +
                    "then the answer is inserted into the database" +
                    "and attached to the post.",
            tags = {"User->Response/Comment"},
            parameters = {
                @Parameter(name = "Auth Key",
                        description = "Bearer key to get access to the endpoint",
                        in = ParameterIn.HEADER,
                        required = true,
                        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                                "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIn0." +
                                "UfFqFnX-9Y8FQj7sy0zNQQipBj8cNt3n3GMf_Rj6iHE",
                        schema = @Schema(type = "String")),
                @Parameter(name = "Text",
                        description = "The text/body of the answer",
                        required = true,
                        example = "You should watch a tutorial",
                        schema = @Schema(type = "String")
                ),
                @Parameter(name = "Post Id",
                        description = "The id of the post that is being answered",
                        required = true,
                        example = "1",
                        schema = @Schema(type = "Long")
                ),
                @Parameter(name = "User Id",
                        description = "The id of the user that is answering",
                        required = true,
                        example = "1",
                        schema = @Schema(type = "Long")
                ),

            },
            method = "POST",
            responses = {@ApiResponse(description = "Answer created", responseCode = "200"),
                    @ApiResponse(description = "Bad request (missing fields)", responseCode = "400"),
                    @ApiResponse(description = "Forbidden. The user doesn't have the permissions to get a properly response.",
                            responseCode = "403")
            }
    )
    public ResponseEntity<DataResponseBody> addResponse(@Valid @RequestBody DataResponse dataResponse){
        return ResponseEntity.ok(responseService.addResponse(dataResponse));
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Updates the answer/comment/response in the post",
            description = "The user sends the information through the body and includes the post id that is watching, the id of the comment and its own id," +
                    "then the answer is updated in the database.",
            tags = {"User->Response/Comment"},
            parameters = {
                @Parameter(name = "Auth Key",
                        description = "Bearer key to get access to the endpoint",
                        in = ParameterIn.HEADER,
                        required = true,
                        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                                "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIn0." +
                                "UfFqFnX-9Y8FQj7sy0zNQQipBj8cNt3n3GMf_Rj6iHE",
                        schema = @Schema(type = "String")),
                @Parameter(name = "Id",
                        description = "The id of the answer to update",
                        required = true,
                        example = "1",
                        schema = @Schema(type = "Long")
                ),
                @Parameter(name = "Text",
                        description = "The text/body of the answer",
                        required = true,
                        example = "You should watch a tutorial",
                        schema = @Schema(type = "String")
                ),
            },
            method = "PUT",
            responses = {@ApiResponse(description = "Answer updated", responseCode = "200"),
                    @ApiResponse(description = "Bad request (missing fields)", responseCode = "400"),
                    @ApiResponse(description = "Forbidden. The user doesn't have the permissions to get a properly response.",
                            responseCode = "403")
            }
    )
    public ResponseEntity<DataResponseBody> updateResponse(@Valid @RequestBody DataUpdateResponse dataUpdateResponse){
        return ResponseEntity.ok(responseService.updateResponse(dataUpdateResponse));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Deletes the answer/comment/response in the post",
            description = "The id of the response is send through the URL and then the service perform a delete action inside the database.",
            tags = {"User->Response/Comment"},
            parameters = {
                @Parameter(name = "Auth Key",
                        description = "Bearer key to get access to the endpoint",
                        in = ParameterIn.HEADER,
                        required = true,
                        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                                "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIn0." +
                                "UfFqFnX-9Y8FQj7sy0zNQQipBj8cNt3n3GMf_Rj6iHE",
                        schema = @Schema(type = "String")),
                @Parameter(name = "Id",
                        description = "The id of the answer to delete",
                        required = true,
                        example = "1",
                        schema = @Schema(type = "Long")
                ),
            },
            method = "DELETE",
            responses = {@ApiResponse(description = "Answer deleted. Returns a string reporting that the answer was succesfully deleted",
                    responseCode = "200"),
                    @ApiResponse(description = "Bad request (missing fields)", responseCode = "400"),
                    @ApiResponse(description = "Forbidden. The user doesn't have the permissions to get a properly response.",
                            responseCode = "403")
            }
    )
    public ResponseEntity deleteResponse(@PathVariable Long id){
        return ResponseEntity.ok(responseService.deletePost(id));
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Mark a response as a solution or unmark it",
            description = "The user sends the id of the response through the URL and then it is updated on the database.",
            tags = {"User->Response/Comment"},
            parameters = {
                @Parameter(name = "Auth Key",
                        description = "Bearer key to get access to the endpoint",
                        in = ParameterIn.HEADER,
                        required = true,
                        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                                "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIn0." +
                                "UfFqFnX-9Y8FQj7sy0zNQQipBj8cNt3n3GMf_Rj6iHE",
                        schema = @Schema(type = "String")),
                @Parameter(name = "Id",
                        in = ParameterIn.PATH,
                        description = "The id of the answer to mark it as a solution",
                        required = true,
                        example = "1",
                        schema = @Schema(type = "Long")
                )
            },
            method = "PUT",
            responses = {@ApiResponse(description = "Answer updated", responseCode = "200"),
                    @ApiResponse(description = "Forbidden. The user doesn't have the permissions to get a properly response.",
                            responseCode = "403")
            }
    )
    public ResponseEntity<DataResponseBody> checkSolution(@PathVariable Long id){
        return ResponseEntity.ok(responseService.checkSolution(id));
    }
}
