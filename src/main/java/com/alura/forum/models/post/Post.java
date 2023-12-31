package com.alura.forum.models.post;

import com.alura.forum.models.course.Course;
import com.alura.forum.models.response.Response;
import com.alura.forum.models.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Embeddable

@Table(name = "posts")
@Entity(name = "Post")
@JsonInclude(JsonInclude.Include.ALWAYS)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Setter

public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_post")
    private StatusPost statusPost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Response> answers = new ArrayList<>();

    @Column(name = "post_date")
    private LocalDateTime postDate;

    public Post(DataPost dataPost, User user, Course course){
        this.title = dataPost.title();
        this.text = dataPost.text();
        this.statusPost = StatusPost.NOT_RESPONDED;
        this.author = user;
        this.course = course;
        this.postDate = LocalDateTime.now();

    }

    public void addAnswer(Response response){
        this.answers.add(response);
    }

}
