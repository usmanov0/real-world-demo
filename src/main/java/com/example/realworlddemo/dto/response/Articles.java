package com.example.realworlddemo.dto.response;


import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;


@Builder
@Getter
public class Articles {
    private String slug;
    private String title;
    private String description;
    private String body;
    private List<String> tagList;
    private boolean favorited;
    private int favoritesCount;
    private AuthorResponse author;
    private Date createdAt;
    private Date updatedAt;
}
