package com.alura.forum.services;

import static com.alura.forum.constants.Constants.USER_ID_NOT_FOUND;
import static com.alura.forum.constants.Constants.COURSE_ID_NOT_FOUND;
import static com.alura.forum.constants.Constants.POST_DELETED_SUCCESSFULLY;
import static com.alura.forum.constants.Constants.POST_PATH;

import com.alura.forum.infra.errors.IntegrityValidations;
import com.alura.forum.models.course.Course;
import com.alura.forum.models.post.*;
import com.alura.forum.models.response.DataResponseBody;
import com.alura.forum.models.user.User;
import com.alura.forum.repositories.CourseRepository;
import com.alura.forum.repositories.PostRepository;
import com.alura.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;

    public ResponseEntity<DataResponsePost> publish(DataPost dataPost, UriComponentsBuilder uriComponentsBuilder){
        if (!userRepository.findById(dataPost.user_id()).isPresent()){
            throw new IntegrityValidations(USER_ID_NOT_FOUND);
        }
        if (!courseRepository.findById(dataPost.course_id()).isPresent()){
            throw new IntegrityValidations(COURSE_ID_NOT_FOUND);
        }
        User user = userRepository.findById(dataPost.user_id()).get();
        Course course = courseRepository.findById(dataPost.course_id()).get();

        Post post = postRepository.save(new Post(dataPost, user, course));

        DataResponsePost dataResponsePost = DataResponsePost.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .status_post(post.getStatusPost().toString())
                .user_id(post.getUser().getId())
                .course_id(post.getCourse().getId())
                .answers(post.getAnswers().stream().map(DataResponseBody::new).collect(Collectors.toList()))
                .post_date(post.getPostDate())
                .build();

        URI url = uriComponentsBuilder.path(POST_PATH).buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(url).body(dataResponsePost);
    }

    public Page<DataListPosts> listPosts(Pageable paginacion){
        return postRepository.findAll(paginacion).map(DataListPosts::new);
    }

    public DataResponsePost viewPost(Long id) {
        Post post = postRepository.getReferenceById(id);

        DataResponsePost dataResponsePost = DataResponsePost.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .status_post(post.getStatusPost().toString())
                .user_id(post.getUser().getId())
                .course_id(post.getCourse().getId())
                .answers(post.getAnswers().stream().map(DataResponseBody::new).collect(Collectors.toList()))
                .post_date(post.getPostDate())
                .build();

       return dataResponsePost;
    }

    public String deletePost(Long id){
        Post post = postRepository.getReferenceById(id);
        postRepository.delete(post);
        return POST_DELETED_SUCCESSFULLY;
    }

    public DataResponsePost updatePost(DataUpdatePost dataUpdatePost) {
        Post post = postRepository.getReferenceById(dataUpdatePost.id());
        Course course = courseRepository.findById(dataUpdatePost.course_id()).get();

        post.setTitle(dataUpdatePost.title());
        post.setText(dataUpdatePost.text());
        post.setCourse(course);

        if(dataUpdatePost.status_post() != null) {
            post.setStatusPost(dataUpdatePost.status_post());
        }
        postRepository.save(post);

        DataResponsePost dataResponsePost = DataResponsePost.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .status_post(post.getStatusPost().toString())
                .user_id(post.getUser().getId())
                .course_id(post.getCourse().getId())
                .answers(post.getAnswers().stream().map(DataResponseBody::new).collect(Collectors.toList()))
                .post_date(post.getPostDate())
                .build();

        return dataResponsePost;
    }
}