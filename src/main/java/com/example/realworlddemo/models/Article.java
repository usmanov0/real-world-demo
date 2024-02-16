package com.example.realworlddemo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Users author;
    private List<Comments> comments;
    private List<Tags> tags;
    private Set<Users> fans;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "favorites")
    public Set<Users> getFans(){return fans;}

    @ManyToOne(fetch = FetchType.EAGER)
    public Users getAuthor(){return author;}

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    public List<Comments> getComments(){return comments;}

    @ManyToMany(fetch = FetchType.EAGER)
    public List<Tags> getTags() {return tags;}
}
