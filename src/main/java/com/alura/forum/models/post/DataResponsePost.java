package com.alura.forum.models.post;

import com.alura.forum.models.response.DataResponseBody;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record DataResponsePost(
        Long id,
        String title,
        String text,
        String statusPost,
        Long userId,
        Long courseId,
        List<DataResponseBody> answers,
        LocalDateTime postDate){
}
