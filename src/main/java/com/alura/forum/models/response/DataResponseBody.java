package com.alura.forum.models.response;

import java.time.LocalDateTime;

public record DataResponseBody(
        Long id,
        String text,
        Boolean solution,
        Long postId,
        Long userId,
        LocalDateTime response_date){

    public DataResponseBody(Response response){
        this(response.getId(), response.getText(), response.getSolution().booleanValue(), response.getPost().getId(), response.getUser().getId(), response.getResponseDate());
    }
}
