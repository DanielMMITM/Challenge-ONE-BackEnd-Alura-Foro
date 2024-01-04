package com.alura.forum.models.post;

import com.alura.forum.models.response.DataResponseBody;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record DataListPosts(
        Long id,
        String title,
        String text,
        String statusPost,
        Long userId,
        Long courseId,
        List<DataResponseBody> answers,
        LocalDateTime postDate)
{
    public DataListPosts(Post post){
        this(post.getId(),
            post.getTitle(),
            post.getText(),
            post.getStatusPost().toString(),
            post.getUser().getId(),
            post.getCourse().getId(),
            post.getAnswers().stream().map(DataResponseBody::new).collect(Collectors.toList()),
            post.getPostDate());
    }
}
