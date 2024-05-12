package com.alura.forum.models.post;

import com.alura.forum.models.response.DataResponseBody;
import com.alura.forum.models.user.User;
import com.alura.forum.models.user.UserInfo;
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
        Long courseId,
        List<DataResponseBody> answers,
        LocalDateTime postDate){
}
