package com.alura.forum.models.response;

import com.alura.forum.models.post.Post;
import com.alura.forum.models.user.User;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Table(name = "responses")
@Entity(name = "Response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Response{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private Boolean solution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "responseDate")
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

    public void setSolution(Boolean mark) {
        this.solution = mark;
    }
}
