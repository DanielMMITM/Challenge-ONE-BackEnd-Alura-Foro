package com.alura.forum.services;

import com.alura.forum.infra.errors.ValidacionDeIntegridad;
import com.alura.forum.models.post.DataResponsePost;
import com.alura.forum.models.post.Post;
import com.alura.forum.models.response.DataResponse;
import com.alura.forum.models.response.DataResponseBody;
import com.alura.forum.models.response.DataUpdateResponse;
import com.alura.forum.models.response.Response;
import com.alura.forum.models.user.User;
import com.alura.forum.repositories.PostRepository;
import com.alura.forum.repositories.ResponseRepository;
import com.alura.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ResponseService {
    public static final String POST_ID_NOT_FOUND = "post id not found";
    public static final String USER_ID_NOT_FOUND = "user id not found";
    public static final String DELETED_SUCCESSFULLY = "Response deleted successfully!";
    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    public DataResponseBody addResponse(DataResponse dataResponse){
        if (!postRepository.findById(dataResponse.post_id()).isPresent()){
            throw new ValidacionDeIntegridad(POST_ID_NOT_FOUND);
        }
        if (!userRepository.findById(dataResponse.user_id()).isPresent()){
            throw new ValidacionDeIntegridad(USER_ID_NOT_FOUND);
        }

        Post post = postRepository.findById(dataResponse.post_id()).get();

        User user = userRepository.findById(dataResponse.user_id()).get();

        Response response = responseRepository.save(new Response(dataResponse, post, user));

        post.addAnswer(response);

        postRepository.save(post);

        DataResponseBody dataResponseBody = DataResponseBody.builder()
                .id(response.getId())
                .text(response.getText())
                .solution(response.getSolution())
                .postId(response.getPost().getId())
                .userId(response.getUser().getId())
                .response_date(response.getResponseDate())
                .build();

        return dataResponseBody;

    }

    public DataResponseBody updateResponse(DataUpdateResponse dataUpdateResponse) {
        Response response = responseRepository.getReferenceById(dataUpdateResponse.id());

        response.setText(dataUpdateResponse.text());

        responseRepository.save(response);

        DataResponseBody dataResponseBody = DataResponseBody.builder()
                .id(response.getId())
                .text(response.getText())
                .solution(response.getSolution())
                .postId(response.getPost().getId())
                .userId(response.getUser().getId())
                .response_date(response.getResponseDate())
                .build();

        return dataResponseBody;
    }


    public String deletePost(Long id) {
        Response response = responseRepository.getReferenceById(id);
        responseRepository.delete(response);
        return DELETED_SUCCESSFULLY;
    }
}
