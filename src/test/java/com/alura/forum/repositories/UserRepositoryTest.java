package com.alura.forum.repositories;

import com.alura.forum.models.role.Role;
import com.alura.forum.models.user.DataSignUpUser;
import com.alura.forum.models.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("It should return null/empty when the user is not in the database")
    void userNotFoundScenario(){
        Optional<User> user = userRepository.findOneByUsername("alura");

        assertThat(user).isEmpty();
    }

    @Test
    @DisplayName("It should return status code 200 when the user is found in the database")
    void existingUserScenario(){
        Optional<User> user1 = Optional.of(createUser("Juan", "juan@gmail.com", "juanito123"));

        Optional<User> user = userRepository.findOneByUsername("Juan");

        assertThat(user1).isEqualTo(user);
    }

    private User createUser(String username, String email, String password){
        User user = new User(dataSignUpUser(username, email, password));
        em.persist(user);
        return user;
    }

    private DataSignUpUser dataSignUpUser(String username, String email, String password){
        return new DataSignUpUser(
                username,
                email,
                password
        );
    }
}
