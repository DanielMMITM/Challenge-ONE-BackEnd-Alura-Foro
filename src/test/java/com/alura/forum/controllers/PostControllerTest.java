package com.alura.forum.controllers;

import com.alura.forum.models.post.*;
import com.alura.forum.services.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class PostControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<DataPost> createPostJacksonTester;
    @Autowired
    private JacksonTester<Page<DataListPosts>> dataListPostsJacksonTester;
    @Autowired
    private JacksonTester<DataResponsePost> dataResponsePostJacksonTester;
    @MockBean
    private PostService postService;

    @Test
    @DisplayName("It should return http status code 400 when missing fields for create a post")
    @WithMockUser
    void createMissingFieldsPostScenario() throws Exception{
        MockHttpServletResponse response = mvc.perform(post("/posts")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("It should return http status code 201 when the fields are valid")
    @WithMockUser
    void createValidPostScenario() throws Exception{
        DataResponsePost data = DataResponsePost.builder()
                .id(null)
                .title("My post")
                .text("This is my post for testing")
                .statusPost(StatusPost.NOT_RESPONDED.toString())
                .userId(1l)
                .courseId(1l)
                .answers(new ArrayList<>())
                .postDate(LocalDateTime.now())
                .build();

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath("/posts");
        URI url = uriComponentsBuilder.path("/posts/{id}").buildAndExpand(data.id()).toUri();

        when(postService.publish(any(), any())).thenReturn(ResponseEntity.created(url).body(data));

        MockHttpServletResponse response = mvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createPostJacksonTester.write(new DataPost("My post", "This is my post for testing", 1l, 1l))
                        .getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        String expectedJson = dataResponsePostJacksonTester.write(data).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @DisplayName("It should return http status code 200 when listing all posts")
    @WithMockUser
    void listAllPostsScenario() throws Exception {

//        DataListPosts post1 = createPostForList(1l, "Post uno", "Este es mi primer post para test", StatusPost.NOT_RESPONDED, 1l, 1l);
//        DataListPosts post2 = createPostForList(2l, "Post dos", "Este es mi segundo post para test", StatusPost.NOT_RESPONDED, 1l, 1l);
//        DataListPosts post3 = createPostForList(3l, "Post tres", "Este es mi tercer post para test", StatusPost.NOT_RESPONDED, 1l, 1l);
//
//        Page<DataListPosts> dataListPosts = new PageImpl<>(Arrays.asList(post1, post2, post3));
//        when(postService.listPosts(any())).thenReturn(dataListPosts);

        MockHttpServletResponse response = mvc.perform(get("/posts")
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

//        String expectedJson = dataListPostsJacksonTester.write(dataListPosts).getJson();

//        assertThat(response.getContentAsString()).isEqualTo(expectedJson.);
    }


//    private DataListPosts createPostForList(Long id, String title, String text, StatusPost statusPost, Long userId, Long courseId){
//        return DataListPosts.builder()
//                .id(id)
//                .title(title)
//                .text(text)
//                .statusPost(statusPost.toString())
//                .userId(userId)
//                .courseId(courseId)
//                .answers(Collections.emptyList())
//                .postDate(LocalDateTime.now())
//                .build();
//    }


}
