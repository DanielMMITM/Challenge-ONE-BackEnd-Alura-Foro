package com.alura.forum.models.response;

import com.alura.forum.models.post.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DataResponseBody(
        Long id,
        String text,
        Boolean solution,
        Long post_id,
        Long user_id,
        LocalDateTime response_date){

    public DataResponseBody(Response response){
        this(response.getId(), response.getText(), response.getSolution().booleanValue(), response.getPost().getId(), response.getAuthor().getId(), response.getResponse_date());
    }
}
