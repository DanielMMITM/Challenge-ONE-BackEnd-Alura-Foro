package com.alura.forum.models.post;

import com.alura.forum.models.response.Response;
import com.alura.forum.models.user.User;

import java.time.LocalDateTime;
import java.util.List;

public record DataListPosts(
        Long id,
        String title,

        String text,

        String status_post,

        Long user_id,

        Long course_id,

        List<Response> answers,

        LocalDateTime post_date){

    public DataListPosts(Post post){
        this(post.getId(), post.getTitle(), post.getText(), post.getStatus_post().toString(), post.getAuthor().getId(), post.getCourse().getId(), post.getAnswers(), post.getPost_date());
    }

}
