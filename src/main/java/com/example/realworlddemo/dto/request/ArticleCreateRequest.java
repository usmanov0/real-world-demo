package com.example.realworlddemo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticleCreateRequest {
    private Article article;

    @Getter
    public class Article{
        private String title;
        private String description;
        private String body;
        @JsonProperty("tagList")
        private List<String> tags;
    }
}
