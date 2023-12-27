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

public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String text;
    @Enumerated(EnumType.STRING)
    private StatusPost status_post = StatusPost.NO_RESPONDIDO;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Response> answers = new ArrayList<>();
    private LocalDateTime post_date = LocalDateTime.now();



}
