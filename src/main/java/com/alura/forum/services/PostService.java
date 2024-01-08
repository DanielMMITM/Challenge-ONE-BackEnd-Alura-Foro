package com.alura.forum.services;

import static com.alura.forum.constants.Constants.POST_ID_NOT_FOUND;
import static com.alura.forum.constants.Constants.USER_ID_NOT_FOUND;
import static com.alura.forum.constants.Constants.COURSE_ID_NOT_FOUND;
import static com.alura.forum.constants.Constants.POST_PATH;
import static com.alura.forum.constants.Constants.POST_DELETED_SUCCESSFULLY;

import com.alura.forum.models.course.Course;
import com.alura.forum.models.post.*;
import com.alura.forum.models.response.DataResponseBody;
import com.alura.forum.models.user.User;
import com.alura.forum.repositories.CourseRepository;
import com.alura.forum.repositories.PostRepository;
import com.alura.forum.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
        if (!userRepository.findById(dataPost.userId()).isPresent()){
            throw new EntityNotFoundException(USER_ID_NOT_FOUND);
        }
        if (!courseRepository.findById(dataPost.courseId()).isPresent()){
            throw new EntityNotFoundException(COURSE_ID_NOT_FOUND);
        }
        User user = userRepository.findById(dataPost.userId()).get();
        Course course = courseRepository.findById(dataPost.courseId()).get();

        Post post = postRepository.save(new Post(dataPost, user, course));

        DataResponsePost dataResponsePost = DataResponsePost.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .statusPost(post.getStatusPost().toString())
                .userId(post.getUser().getId())
                .courseId(post.getCourse().getId())
                .answers(post.getAnswers().stream().map(DataResponseBody::new).collect(Collectors.toList()))
                .postDate(post.getPostDate())
                .build();

        URI url = uriComponentsBuilder.path(POST_PATH).buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(url).body(dataResponsePost);
    }

    public Page<DataListPosts> listPosts(Pageable pagination){
        return postRepository.findAll(pagination).map(DataListPosts::new);
    }

    public DataResponsePost viewPost(Long id) {
        if (!postRepository.findById(id).isPresent()){
            throw new EntityNotFoundException(POST_ID_NOT_FOUND);
        }
        Post post = postRepository.getReferenceById(id);

       return DataResponsePost.builder()
               .id(post.getId())
               .title(post.getTitle())
               .text(post.getText())
               .statusPost(post.getStatusPost().toString())
               .userId(post.getUser().getId())
               .courseId(post.getCourse().getId())
               .answers(post.getAnswers().stream().map(DataResponseBody::new).collect(Collectors.toList()))
               .postDate(post.getPostDate())
               .build();
    }

    public String deletePost(Long id){
        if (!postRepository.findById(id).isPresent()){
            throw new EntityNotFoundException(POST_ID_NOT_FOUND);
        }
        Post post = postRepository.getReferenceById(id);
        postRepository.delete(post);
        return POST_DELETED_SUCCESSFULLY;
    }

    public DataResponsePost updatePost(DataUpdatePost dataUpdatePost) {
        if (!postRepository.findById(dataUpdatePost.id()).isPresent()){
            throw new EntityNotFoundException(POST_ID_NOT_FOUND);
        }
        if (!courseRepository.findById(dataUpdatePost.courseId()).isPresent()){
            throw new EntityNotFoundException(COURSE_ID_NOT_FOUND);
        }
        Post post = postRepository.getReferenceById(dataUpdatePost.id());
        Course course = courseRepository.findById(dataUpdatePost.courseId()).get();

        post.setTitle(dataUpdatePost.title());
        post.setText(dataUpdatePost.text());
        post.setCourse(course);

        if(dataUpdatePost.statusPost() != null) {
            post.setStatusPost(dataUpdatePost.statusPost());
        }
        postRepository.save(post);

        return DataResponsePost.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .statusPost(post.getStatusPost().toString())
                .userId(post.getUser().getId())
                .courseId(post.getCourse().getId())
                .answers(post.getAnswers().stream().map(DataResponseBody::new).collect(Collectors.toList()))
                .postDate(post.getPostDate())
                .build();
    }
}