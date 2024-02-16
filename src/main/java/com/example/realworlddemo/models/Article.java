package com.example.realworlddemo.models;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
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

    @ManyToOne(fetch = FetchType.EAGER)
    private Users author;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
    private List<Comments> comments;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "favorites")
    private Set<Users> fans;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Tags> tags;


//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "favorites")
//    public Set<Users> getFans(){return fans;}
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    public Users getAuthor(){return author;}
//
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "article_id")
//    public List<Comments> getComments(){return comments;}
//
//    @ManyToMany(fetch = FetchType.EAGER)
//    public List<Tags> getTags() {return tags;}
}
