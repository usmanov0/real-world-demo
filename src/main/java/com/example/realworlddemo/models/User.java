package com.example.realworlddemo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String bio;
    private String image;
    private String username;
    private Set<Article> favorite;
    private Set<User> followers;

    @ManyToMany(mappedBy = "fans", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Article> getFavorite(){return favorite;}

    @ManyToMany
    @JoinTable(name = "user_followers")
    public Set<User> getFollowers() {return followers;}
}
