package com.alura.forum.services;

import com.alura.forum.infra.errors.ValidacionDeIntegridad;
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
        if (!postRepository.findById(dataResponse.post_id()).isPresent()){
            throw new ValidacionDeIntegridad("Este id para el post no fue encontrado");
        }
        if (!userRepository.findById(dataResponse.user_id()).isPresent()){
            throw new ValidacionDeIntegridad("Este id para el usuario no fue encontrado");
        }

        Post post = postRepository.findById(dataResponse.post_id()).get();

        User user = userRepository.findById(dataResponse.user_id()).get();

        Response response = responseRepository.save(new Response(dataResponse, post, user));

        post.addAnswer(response);

        postRepository.save(post);

//        DataResponsePost dataResponsePost = new DataResponsePost(post.getId(), post.getTitle(), post.getText(), post.getStatus_post().toString(),
//                post.getAuthor().getId(), post.getCourse().getId(), post.getAnswers(), post.getPost_date());

//        URI url = uriComponentsBuilder.path("/posts/{id}").buildAndExpand(post.getId()).toUri();
        return new DataResponseBody(response.getId(), response.getText(), response.getSolution(),
                response.getPost().getId(), response.getUser().getId(), response.getResponseDate());

    }

    public DataResponseBody updateResponse(DataUpdateResponse dataUpdateResponse) {
        Response response = responseRepository.getReferenceById(dataUpdateResponse.id());

        response.setText(dataUpdateResponse.text());

        responseRepository.save(response);

        return new DataResponseBody(response.getId(), response.getText(), response.getSolution(),
                response.getPost().getId(), response.getUser().getId(), response.getResponseDate());
    }


    public String deletePost(Long id) {
        Response response = responseRepository.getReferenceById(id);
        responseRepository.delete(response);
        return "Response deleted successfully!";
    }
}
