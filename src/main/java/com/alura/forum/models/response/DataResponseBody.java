package com.alura.forum.models.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DataResponseBody(
        Long id,
        String text,
        Boolean solution,
        Long post_id,
        Long user_id,
        LocalDateTime response_date){
}
