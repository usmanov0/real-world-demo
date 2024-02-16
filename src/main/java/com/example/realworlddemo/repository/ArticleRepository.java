package com.example.realworlddemo.repository;

import com.example.realworlddemo.models.Article;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    Article findArticleBySlug(@Param("slug")String slug);


    @Query(value = "select * from  articles as a where  a.author.id in (select user_id from user_followers as uf where" +
            "followers_id = ?1 ) order by created_at desc",nativeQuery = true)
    List<Article> findArticleFeed(Long userId, Pageable pageable);


    @Query(value = "select distinct(id), slug, title, description, body, author_id, created_at, updated_at, from (select a.* from" +
            " articles a inner join users u on a.author_id = u.id left join articles_tags at2 on a.id = at2.articles_id " +
            "full outer join tags t on at2.tags_id = t.id full outer join favourites f on f.favorited_id = a.id where " +
            "t.name = :tag or u.username = :author or f.fans_id = (select id from users where username = :fav)) dd order by created_at desc",
            nativeQuery = true)
    List<Article> findByAuthorOrTagOrFavorited(
            @Param("tag") String tag,
            @Param("author") String author,
            @Param("fav") String favorited,
            org.springframework.data.domain.Pageable pageable
    );

    @Modifying
    @Transactional
    @Query(value = "delete from articles as a where a.slug = ?1",nativeQuery = true)
    void deleteBySlug(String slug);

    @Modifying
    @Transactional
    @Query(value = "delete from favorites where favorite_id = ?2 and fans_id = ?1",nativeQuery = true)
    void unfavoriteArticle(Long userId, Long articleId);
}
