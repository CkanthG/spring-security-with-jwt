package com.sree.jwt.api;

import com.sree.jwt.api.entity.User;
import com.sree.jwt.api.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SpringSecurityWithJwtApplication {

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void loadUsers() {
        List<User> userList = Arrays.asList(
                new User(1, "sreekanth", "password", "sree@gmail.com"),
                new User(2, "sowmya", "pass1", "sow@gmail.com"),
                new User(3, "devansh", "pass2", "dev@gmail.com")
        );
        userRepository.saveAll(userList);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityWithJwtApplication.class, args);
    }

}
