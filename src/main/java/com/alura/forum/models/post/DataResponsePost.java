package com.alura.forum.models.post;

import com.alura.forum.models.response.DataResponseBody;
import com.alura.forum.models.response.Response;


import java.time.LocalDateTime;
import java.util.List;

public record DataResponsePost(
        Long id,
        String title,

        String text,

        String status_post,

        Long user_id,

        Long course_id,

        List<DataResponseBody> answers,

        LocalDateTime post_date){
}
