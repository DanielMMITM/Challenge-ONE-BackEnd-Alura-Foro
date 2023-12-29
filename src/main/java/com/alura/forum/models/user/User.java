package com.alura.forum.models.user;

import jakarta.persistence.*;
import lombok.*;


@Table(name = "users")
@Entity(name = "User")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    public User (DataSignUpUser dataSignUpUser){
        this.name = dataSignUpUser.name();
        this.email = dataSignUpUser.email();
        this.password = dataSignUpUser.password();
    }
}
