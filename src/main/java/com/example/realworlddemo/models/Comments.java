package com.example.realworlddemo.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String body;
    private Users author;
    private Article article;

    @ManyToOne(targetEntity = Users.class)
    public Users getAuthor(){return author;}

    @ManyToOne(targetEntity = Article.class)
    public Article getArticle(){return article;}
}
