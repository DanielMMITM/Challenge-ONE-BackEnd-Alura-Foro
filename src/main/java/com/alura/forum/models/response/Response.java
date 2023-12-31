package com.alura.forum.models.response;

import com.alura.forum.models.post.Post;
import com.alura.forum.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "responses")
@Entity(name = "Response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Response implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private Boolean solution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "response_date")
    private LocalDateTime responseDate;

    public Response(DataResponse dataResponse, Post post, User user){
        this.text = dataResponse.text();
        this.solution = false;
        this.post = post;
        this.user = user;
        this.responseDate = LocalDateTime.now();
    }

    public void setText(String text) {
        this.text = text;
    }
}
