package com.alura.forum.controllers;

import com.alura.forum.models.response.DataResponse;
import com.alura.forum.models.response.DataResponseBody;
import com.alura.forum.models.response.DataUpdateResponse;
import com.alura.forum.services.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/responses")
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
            method = "POST",
            responses = {@ApiResponse(description = "Answer created", responseCode = "200"),
                    @ApiResponse(description = "Bad request (missing fields)", responseCode = "400"),
                    @ApiResponse(description = "Forbidden. The user doesn't have the permissions to get a properly response.", responseCode = "403")
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
            method = "PUT",
            responses = {@ApiResponse(description = "Answer updated", responseCode = "200"),
                    @ApiResponse(description = "Bad request (missing fields)", responseCode = "400"),
                    @ApiResponse(description = "Forbidden. The user doesn't have the permissions to get a properly response.", responseCode = "403")
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
            method = "DELETE",
            responses = {@ApiResponse(description = "Answer deleted. Returns a string reporting that the answer was succesfully deleted", responseCode = "200"),
                    @ApiResponse(description = "Bad request (missing fields)", responseCode = "400"),
                    @ApiResponse(description = "Forbidden. The user doesn't have the permissions to get a properly response.", responseCode = "403")
            }
    )
    public ResponseEntity deleteResponse(@PathVariable Long id){
        return ResponseEntity.ok(responseService.deletePost(id));
    }
}
