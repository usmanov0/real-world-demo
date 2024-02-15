package com.example.realworlddemo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String slug;
    private String title;
    private String body;
    private User author;
    private List<Comment> commentList;
    private List<Tag> tags;
    private Set<User> fans;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "favourites")
    public Set<User> getFans(){return fans;}

    @ManyToOne(fetch = FetchType.EAGER)
    public User getAuthor(){return author;}

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    public List<Comment> getComments(){return commentList;}

    @ManyToMany(fetch = FetchType.EAGER)
    public List<Tag> getTags() {return tags;}
}
