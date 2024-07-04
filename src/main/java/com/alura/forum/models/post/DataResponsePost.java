package com.alura.forum.models.post;

import com.alura.forum.models.course.Course;
import com.alura.forum.models.response.DataResponseBody;
import com.alura.forum.models.user.User;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record DataResponsePost(
        Long id,
        String title,
        String text,
        String statusPost,
        User userCreator,
        Course course,
        List<DataResponseBody> answers,
        LocalDateTime postDate){
}
