package com.alura.forum.services;

import static com.alura.forum.constants.Constants.USER_ID_NOT_FOUND;
import static com.alura.forum.constants.Constants.POST_ID_NOT_FOUND;
import static com.alura.forum.constants.Constants.RESPONSE_ID_NOT_FOUND;
import static com.alura.forum.constants.Constants.RESPONSE_DELETED_SUCCESSFULLY;

import com.alura.forum.models.post.Post;
import com.alura.forum.models.response.DataResponse;
import com.alura.forum.models.response.DataResponseBody;
import com.alura.forum.models.response.DataUpdateResponse;
import com.alura.forum.models.response.Response;
import com.alura.forum.models.user.User;
import com.alura.forum.repositories.PostRepository;
import com.alura.forum.repositories.ResponseRepository;
import com.alura.forum.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
            throw new EntityNotFoundException(POST_ID_NOT_FOUND);
        }
        if (!userRepository.findById(dataResponse.userId()).isPresent()){
            throw new EntityNotFoundException(USER_ID_NOT_FOUND);
        }
        Post post = postRepository.findById(dataResponse.postId()).get();
        User user = userRepository.findById(dataResponse.userId()).get();

        Response response = responseRepository.save(new Response(dataResponse, post, user));
        post.addAnswer(response);
        postRepository.save(post);

        return DataResponseBody.builder()
                .id(response.getId())
                .text(response.getText())
                .solution(response.getSolution())
                .postId(response.getPost().getId())
                .userCreator(user)
                .responseDate(response.getResponseDate())
                .build();
    }

    public DataResponseBody updateResponse(DataUpdateResponse dataUpdateResponse) {
        if (!responseRepository.findById(dataUpdateResponse.id()).isPresent()){
            throw new EntityNotFoundException(RESPONSE_ID_NOT_FOUND);
        }
        Response response = responseRepository.getReferenceById(dataUpdateResponse.id());
        User user = userRepository.getReferenceById(response.getUser().getId());
        response.setText(dataUpdateResponse.text());
        responseRepository.save(response);

        return DataResponseBody.builder()
                .id(response.getId())
                .text(response.getText())
                .solution(response.getSolution())
                .postId(response.getPost().getId())
                .userCreator(user)
                .responseDate(response.getResponseDate())
                .build();
    }

    public String deletePost(Long id) {
        if (!responseRepository.findById(id).isPresent()){
            throw new EntityNotFoundException(RESPONSE_ID_NOT_FOUND);
        }
        Response response = responseRepository.getReferenceById(id);
        responseRepository.delete(response);
        return RESPONSE_DELETED_SUCCESSFULLY;
    }

    public DataResponseBody checkSolution(Long id) {
        if (!responseRepository.findById(id).isPresent()){
            throw new EntityNotFoundException(RESPONSE_ID_NOT_FOUND);
        }
        Response response = responseRepository.getReferenceById(id);
        User user = userRepository.getReferenceById(response.getUser().getId());
        response.setSolution(!response.getSolution());
        responseRepository.save(response);
        return DataResponseBody.builder()
                .id(response.getId())
                .text(response.getText())
                .solution(response.getSolution())
                .postId(response.getPost().getId())
                .userCreator(user)
                .responseDate(response.getResponseDate())
                .build();
    }
}
