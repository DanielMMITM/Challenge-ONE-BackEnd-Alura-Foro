package com.alura.forum.controllers;


import com.alura.forum.models.post.DataResponsePost;
import com.alura.forum.models.response.DataResponse;
import com.alura.forum.models.response.DataResponseBody;
import com.alura.forum.services.ResponseService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/responses")
public class ResponseController {

    @Autowired
    private ResponseService responseService;

    @PostMapping
    @Transactional
    public ResponseEntity<DataResponseBody> addResponse(@Valid @RequestBody DataResponse dataResponse){
        return ResponseEntity.ok(responseService.addResponse(dataResponse));
    }
}
