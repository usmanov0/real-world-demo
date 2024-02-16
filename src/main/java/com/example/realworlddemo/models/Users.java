package com.example.realworlddemo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String bio;
    private String image;
    private String username;

    @ManyToMany(mappedBy = "fans", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Article> favorite;

    @ManyToMany
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private Set<Users> followers;
}
