package com.example.realworlddemo.controllers;

import com.example.realworlddemo.converter.ArticleObjectConverter;
import com.example.realworlddemo.converter.CommentObjectConverter;
import com.example.realworlddemo.dto.request.ArticleCreateRequest;
import com.example.realworlddemo.dto.request.ArticleUpdateRequest;
import com.example.realworlddemo.dto.request.CommentCreateRequest;
import com.example.realworlddemo.dto.response.ArticleResponse;
import com.example.realworlddemo.dto.response.Comment;
import com.example.realworlddemo.dto.response.CommentResponse;
import com.example.realworlddemo.models.Article;
import com.example.realworlddemo.models.Comments;
import com.example.realworlddemo.models.Users;
import com.example.realworlddemo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    public final ArticleService articleService;
    ArticleObjectConverter articleObjectConverter;
    CommentObjectConverter commentObjectConverter;

    @Autowired
    public ArticleController(ArticleService articleService, ArticleObjectConverter articleObjectConverter, CommentObjectConverter commentObjectConverter) {
        this.articleService = articleService;
        this.articleObjectConverter = articleObjectConverter;
        this.commentObjectConverter = commentObjectConverter;
    }

    @PostMapping(value = "/new", produces = "application/json")
    public ResponseEntity<ArticleResponse> createArticle(
            @RequestBody ArticleCreateRequest body,
            @AuthenticationPrincipal Users userEntity){
        var articleEntity = articleService.createArticle(
                body.getArticle().getTitle(),
                body.getArticle().getDescription(),
                body.getArticle().getBody(),
                body.getArticle().getTags(),
                userEntity);

        return ResponseEntity.ok(articleObjectConverter.entityToResponse(articleEntity));
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<ArticleResponse>> getArticles(
            @RequestParam(value = "tag", required = false, defaultValue = "10") String tag,
            @RequestParam(value = "author", required = false, defaultValue = "") String author,
            @RequestParam(value = "favorited", required = false, defaultValue = "10") String favorited,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset){
        var articleEntity = articleService.getAllArticleList(author, tag,favorited,offset,limit);
        List<ArticleResponse> articleResponseList = new ArrayList<>();
        articleEntity.forEach(article -> articleResponseList.add(articleObjectConverter.entityToResponse(article)));
        return ResponseEntity.ok(articleResponseList);
    }

    @GetMapping(value = "/feed", produces = "application/json")
    public ResponseEntity<List<ArticleResponse>> getArticleFeed(
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @AuthenticationPrincipal Users userEntity){
        var article = articleService.getArticleFeed(userEntity, limit, offset);
        List<ArticleResponse> articleResponseList = article.stream()
                .map(article1 -> articleObjectConverter.entityToResponse(article1))
                .collect(Collectors.toList());
        return ResponseEntity.ok(articleResponseList);
    }

    @GetMapping(value = "/{slug}", produces = "application/json")
    public ResponseEntity<ArticleResponse> getArticleBySlug(@PathVariable(value = "slug") String slug){
        var article = articleService.getArticleBySlug(slug);
        return ResponseEntity.ok(articleObjectConverter.entityToResponse(article));
    }

    @PutMapping(value = "/{slug}", produces = "application/json")
    public ResponseEntity<ArticleResponse> updateArticle(
            @PathVariable(value = "slug", required = true) String slug,
            @RequestBody ArticleUpdateRequest body,
            @AuthenticationPrincipal Users userEntity){
        Article articleEntity = articleService.updateArticle(body.getArticle(), slug, userEntity);
        return ResponseEntity.ok(articleObjectConverter.entityToResponse(articleEntity));
    }

    @PostMapping(value = "/{slug}/comments", produces = "application/json")
    public ResponseEntity<CommentResponse> addCommentToArticle(
            @PathVariable(value = "slug") String slug,
            @RequestBody CommentCreateRequest body,
            @AuthenticationPrincipal Users userEntity){
        Comments comments = articleService.addCommentToArticle(slug, body.getComment(), userEntity);
        return ResponseEntity.ok(commentObjectConverter.entityToResponse(comments));
    }

    @GetMapping(value = "/{slug}/comments", produces = "application/json")
    public ResponseEntity<List<Comment>> getCommentList(@PathVariable(value = "slug") String slug){
        List<Comments> comments = articleService.getAllCommentsBySlug(slug);
        return ResponseEntity.ok(commentObjectConverter.entityToResponse(comments));
    }

    @DeleteMapping(value = "/{slug}/comments/{id}", produces = "application/json")
    public ResponseEntity<String> deleteComment(
            @PathVariable(value = "id") Long id,
            @AuthenticationPrincipal Users user){
        articleService.deleteComment(id, user);
        return ResponseEntity.ok("comment deleted");
    }

    @DeleteMapping(value = "/{slug}", produces = "application/json")
    public ResponseEntity<String> deleteArticle(@PathVariable(value = "slug", required = true) String slug,
                                                @AuthenticationPrincipal Users userEntity){
        articleService.deleteArticleBySlug(slug, userEntity);
        return ResponseEntity.ok("articles deleted");
    }
 }
