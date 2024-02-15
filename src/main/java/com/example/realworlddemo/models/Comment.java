package com.example.realworlddemo.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String body;
    private User author;
    private Article article;

    @ManyToOne(targetEntity = User.class)
    public User getAuthor(){return author;}

    @ManyToOne(targetEntity = Article.class)
    public Article getArticle(){return article;}
}
