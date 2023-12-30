package com.alura.forum.models.post;

import com.alura.forum.models.response.DataResponseBody;
import com.alura.forum.models.response.Response;
import com.alura.forum.models.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record DataListPosts(
        Long id,
        String title,

        String text,

        String status_post,

        Long user_id,

        Long course_id,

        List<DataResponseBody> answers,

        LocalDateTime post_date){

    public DataListPosts(Post post){
        this(post.getId(), post.getTitle(), post.getText(), post.getStatus_post().toString(), post.getAuthor().getId(), post.getCourse().getId(), post.getAnswers().stream().map(DataResponseBody::new).collect(Collectors.toList()), post.getPost_date());
    }

}
