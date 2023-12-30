package com.alura.forum.services;

import com.alura.forum.infra.ValidacionDeIntegridad;
import com.alura.forum.models.course.Course;
import com.alura.forum.models.post.*;
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
            throw new ValidacionDeIntegridad("Este id para el usuario no fue encontrado");
        }
        if (!courseRepository.findById(dataPost.course_id()).isPresent()){
            throw new ValidacionDeIntegridad("Este id para el curso no fue encontrado");
        }

        User user = userRepository.findById(dataPost.user_id()).get();
        Course course = courseRepository.findById(dataPost.course_id()).get();

        Post post = postRepository.save(new Post(dataPost, user, course));
        DataResponsePost dataResponsePost = new DataResponsePost(post.getId(), post.getTitle(), post.getText(), post.getStatus_post().toString(),
                post.getAuthor().getId(), post.getCourse().getId(), post.getAnswers(), post.getPost_date());
        URI url = uriComponentsBuilder.path("/posts/{id}").buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(url).body(dataResponsePost);
    }

    public Page<DataListPosts> listPosts(Pageable paginacion){
        return postRepository.findAll(paginacion).map(DataListPosts::new);
    }

    public DataResponsePost viewPost(Long id){
        Post post = postRepository.getReferenceById(id);
        DataResponsePost dataResponsePost = new DataResponsePost(post.getId(), post.getTitle(), post.getText(), post.getStatus_post().toString(), post.getAuthor().getId()
                ,post.getCourse().getId(), post.getAnswers(),post.getPost_date());
        return dataResponsePost;
    }

    public String deletePost(Long id){
        Post post = postRepository.getReferenceById(id);
        postRepository.delete(post);
        return "Post deleted successfully!";
    }

    public DataResponsePost updatePost(DataUpdatePost dataUpdatePost) {
        Post post = postRepository.getReferenceById(dataUpdatePost.id());

        Course course = courseRepository.findById(dataUpdatePost.course_id()).get();

        post.setTitle(dataUpdatePost.title());
        post.setText(dataUpdatePost.text());
        post.setCourse(course);

        if(dataUpdatePost.status_post() != null) {
            post.setStatus_post(dataUpdatePost.status_post());
        }

        postRepository.save(post);

        DataResponsePost dataResponsePost = new DataResponsePost(post.getId(), post.getTitle(), post.getText(), post.getStatus_post().toString(), post.getAuthor().getId()
                ,post.getCourse().getId(), post.getAnswers(),post.getPost_date());

        return dataResponsePost;
    }
}