package com.alura.forum.models.post;

import com.alura.forum.models.course.Course;
import com.alura.forum.models.response.Response;
import com.alura.forum.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Embeddable

@Table(name = "posts")
@Entity(name = "Post")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Setter

public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String text;

    @Enumerated(EnumType.STRING)
    private StatusPost status_post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Response> answers = new ArrayList<>();

    private LocalDateTime post_date;

    public Post(DataPost dataPost, User user, Course course){
        this.title = dataPost.title();
        this.text = dataPost.text();
        this.status_post = StatusPost.NOT_RESPONDED;
        this.author = user;
        this.course = course;
        this.post_date = LocalDateTime.now();
    }

}
