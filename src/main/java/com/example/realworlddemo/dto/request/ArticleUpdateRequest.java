package com.example.realworlddemo.dto.request;

import com.example.realworlddemo.models.Article;
import lombok.Getter;

@Getter
public class ArticleUpdateRequest {
    private Article article;

    @Getter
    public class Article{
        private String title;
        private String  description;
        private String body;
    }
}
