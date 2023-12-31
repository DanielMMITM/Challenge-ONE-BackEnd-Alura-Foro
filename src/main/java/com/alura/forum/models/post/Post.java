package com.alura.forum.models.post;

import com.alura.forum.models.course.Course;
import com.alura.forum.models.response.Response;
import com.alura.forum.models.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "posts")
@Entity(name = "Post")
@JsonInclude(JsonInclude.Include.ALWAYS)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Setter
public class Post{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusPost")
    private StatusPost statusPost;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Response> answers = new ArrayList<>();

    @Column(name = "postDate")
    private LocalDateTime postDate;

    public Post(DataPost dataPost, User user, Course course){
        this.title = dataPost.title();
        this.text = dataPost.text();
        this.statusPost = StatusPost.NOT_RESPONDED;
        this.user = user;
        this.course = course;
        this.postDate = LocalDateTime.now();

    }

    public void addAnswer(Response response){
        this.answers.add(response);
    }

}
