package org.sopt.domain.user.domain;

import jakarta.persistence.*;
import org.sopt.domain.post.domain.Post;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();


    protected User() {}

    public User(String name, String email) {
        validate(name);
        this.name = name;
        this.email = email;
    }

    private void validate(String name) {
        validateName(name);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
    }


    public static User create(String name, String email) {
        return new User(name, email);
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }


}