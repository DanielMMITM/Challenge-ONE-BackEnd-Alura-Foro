package com.alura.forum.services;

import static com.alura.forum.constants.Constants.USER_ID_NOT_FOUND;
import static com.alura.forum.constants.Constants.POST_ID_NOT_FOUND;
import static com.alura.forum.constants.Constants.RESPONSE_DELETED_SUCCESSFULLY;

import com.alura.forum.infra.errors.IntegrityValidations;
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

@Service
public class ResponseService {
    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostService postService;

    public DataResponseBody addResponse(DataResponse dataResponse){
        if (!postRepository.findById(dataResponse.postId()).isPresent()){
            throw new IntegrityValidations(POST_ID_NOT_FOUND);
        }
        if (!userRepository.findById(dataResponse.userId()).isPresent()){
            throw new IntegrityValidations(USER_ID_NOT_FOUND);
        }
        Post post = postRepository.findById(dataResponse.postId()).get();
        User user = userRepository.findById(dataResponse.userId()).get();

        Response response = responseRepository.save(new Response(dataResponse, post, user));
        post.addAnswer(response);
        postRepository.save(post);

        DataResponseBody dataResponseBody = DataResponseBody.builder()
                .id(response.getId())
                .text(response.getText())
                .solution(response.getSolution())
                .postId(response.getPost().getId())
                .userId(response.getUser().getId())
                .responseDate(response.getResponseDate())
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
                .responseDate(response.getResponseDate())
                .build();

        return dataResponseBody;
    }

    public String deletePost(Long id) {
        Response response = responseRepository.getReferenceById(id);
        responseRepository.delete(response);
        return RESPONSE_DELETED_SUCCESSFULLY;
    }

    public DataResponseBody checkSolution(Long id) {
        Response response = responseRepository.getReferenceById(id);
        response.setSolution(!response.getSolution());
        responseRepository.save(response);
        return DataResponseBody.builder()
                .id(response.getId())
                .text(response.getText())
                .solution(response.getSolution())
                .postId(response.getPost().getId())
                .userId(response.getUser().getId())
                .responseDate(response.getResponseDate())
                .build();
    }
}
