package com.example.realworlddemo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "articles")
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String slug;
    private String title;
    private String description;
    private String body;

    @ManyToOne(fetch = FetchType.EAGER)
    private Users author;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
    private List<Comments> comments;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "favorites")
    private Set<Users> fans;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Tags> tags;

    private Date createdAt;
    private Date updatedAt;
}
