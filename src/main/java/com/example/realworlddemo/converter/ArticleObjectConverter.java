package com.example.realworlddemo.converter;

import com.example.realworlddemo.dto.response.ArticleResponse;
import com.example.realworlddemo.dto.response.Articles;
import com.example.realworlddemo.dto.response.AuthorResponse;
import com.example.realworlddemo.models.Article;
import com.example.realworlddemo.models.Users;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleObjectConverter {
    public ArticleResponse entityToResponse(Article articleEntity){
        List<String> tagName = new ArrayList<>();
        articleEntity.getTags().forEach(tags -> {
            tagName.add(tags.getName());
        });

        Users author = articleEntity.getAuthor();
        var authorResponse = AuthorResponse.builder()
                .username(author.getUsername())
                .bio(author.getBio())
                .image(author.getImage())
                .following(false)
                .build();

        return new ArticleResponse(Articles.builder().slug(articleEntity.getSlug())
                .slug(articleEntity.getSlug())
                .title(articleEntity.getTitle())
                .description(articleEntity.getDescription())
                .body(articleEntity.getBody())
                .tagList(tagName)
                .favoritesCount(0)
                .favorited(false)
                .author(authorResponse)
                .createdAt(articleEntity.getCreatedAt())
                .updatedAt(articleEntity.getUpdatedAt())
                .build());
    }
}
