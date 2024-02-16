package com.example.realworlddemo.service;


import com.example.realworlddemo.dto.ArticleUpdateRequest;
import com.example.realworlddemo.dto.CommentCreateReq;
import com.example.realworlddemo.exceptions.AccessDeniedException;
import com.example.realworlddemo.models.Article;
import com.example.realworlddemo.models.Comments;
import com.example.realworlddemo.models.Users;
import com.example.realworlddemo.repository.ArticleRepository;
import com.example.realworlddemo.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Locale;
import static java.util.Objects.isNull;


@Service
public class ArticleService {
    private final ArticleRepository articleRepo;
    private final CommentRepository commentRepo;
    protected final TagService tagService;


    public ArticleService(ArticleRepository articleRepo, CommentRepository commentRepo, TagService tagService) {
        this.articleRepo = articleRepo;
        this.commentRepo = commentRepo;
        this.tagService = tagService;
    }

    public List<Article> getAllArticles(int limit, int offset){
        PageRequest pageRequest = PageRequest.of((offset / limit),limit);
        Page<Article> articlePage = articleRepo.findAll(pageRequest);
        return articlePage.getContent();
    }

    public Article getArticleBySlug(String slug){
        return articleRepo.findArticleBySlug(slug);
    }

    public String getSlug(String title){
        return title.toLowerCase(Locale.ROOT).replace(' ','-');
    }

    public Article createArticle(String articleTitle, String articleDescription, String articleBody, List<String> articleTagList, Users currentLoggedInUser){
        var articleTag = tagService.createTags(articleTagList);
        var articleSlug = this.getSlug(articleTitle);
        var article = Article.builder()
                .slug(articleSlug)
                .title(articleTitle)
                .description(articleDescription)
                .body(articleBody)
                .author(currentLoggedInUser)
                .tags(articleTag)
                .build();
        return articleRepo.save(article);
    }

    public List<Article> getArticleFeed(Users user, int limit, int offset){
        PageRequest pr = PageRequest.of((offset / limit),limit);
        return articleRepo.findArticleFeed(user.getId(), (Pageable) pr);
    }


    public List<Article> getAllArticleList(String tag, String author, String favorited, int limit, int offset){
        PageRequest pageRequest = PageRequest.of((offset / limit),limit);
        return articleRepo.findByAuthorOrTagOrFavorited(tag,author,favorited,pageRequest);
    }

    public Article updateArticle(ArticleUpdateRequest.Article article, String slug, Users user){
        Article oldArticle = articleRepo.findArticleBySlug(slug);
        if (oldArticle.getAuthor().getId() != user.getId()){
            throw new AccessDeniedException();
        }
        if (!isNull(article.getBody())) oldArticle.setBody(article.getBody());
        if (!isNull(article.getDescription())) oldArticle.setDescription(article.getDescription());
        if (!isNull(article.getTitle())) {
            oldArticle.setTitle(article.getTitle());
            oldArticle.setSlug(getSlug(article.getTitle()));
        }
        return articleRepo.save(oldArticle);
    }

    public void deleteArticleBySlug(String slug, Users userEntity){
        Article article = getArticleBySlug(slug);
        if (article.getAuthor().getId() != userEntity.getId())
            throw new AccessDeniedException();

        articleRepo.deleteBySlug(slug);
    }

    public Comments addCommentToArticle(String slug, CommentCreateReq.Comment  body, Users currentLoggedInUser){
        Article article = getArticleBySlug(slug);
        Comments comment = Comments.builder()
                .body(body.getBody())
                .author(currentLoggedInUser)
                .article(article)
                .build();
        return commentRepo.save(comment);
    }

    public List<Comments> getAllCommentsBySlug(String slug){
        Article article = getArticleBySlug(slug);
        return commentRepo.findCommentByArticle(article);
    }

    public void deleteComment(Long id, Users user){
        Comments comment = commentRepo.getOne(id);
        if (user.getId() != comment.getAuthor().getId()){
            throw new AccessDeniedException();
        }

        commentRepo.deleteById(id);
    }

    public Article favoriteArticle(String slug, Users user){
        Article article = getArticleBySlug(slug);
        article.getFans().add(user);
        return articleRepo.saveAndFlush(article);
    }

    public Article unfavoriteArticle(String slug, Users user){
        Article article =  getArticleBySlug(slug);
        articleRepo.unfavoriteArticle(user.getId(), article.getId());
        return article;
    }
}
