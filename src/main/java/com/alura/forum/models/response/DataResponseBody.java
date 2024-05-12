package com.alura.forum.models.response;

import com.alura.forum.models.user.User;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record DataResponseBody(
        Long id,
        String text,
        Boolean solution,
        Long postId,
        User userCreator,
        LocalDateTime responseDate)
{
    public DataResponseBody(Response response){
        this(response.getId(),
            response.getText(),
            response.getSolution().booleanValue(),
            response.getPost().getId(),
            response.getUser(),
            response.getResponseDate());
    }
}
