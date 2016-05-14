package pl.pwr.news.service.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.service.exception.ArticleNotExist;
import pl.pwr.news.service.exception.NotUniqueArticle;

import java.util.Date;
import java.util.List;

/**
 * Created by jakub on 2/29/16.
 */
@Service
public interface ArticleService {

    List<Article> findAll(String keyword, String link);

    void addTag(Long articleId, Long... tagId);

    void addCategory(Long articleId, Long categoryId);

    void removeTag(Long articleId, Long... tagId);

    Article createOrUpdate(Article article);

    Article create(Article article) throws NotUniqueArticle;

    Article update(Article article) throws ArticleNotExist;

    List<Article> findAll();

    boolean unique(String sourceUrl);

    Long countAll();

    Page<Article> findAll(Pageable pageable);

    Article findById(Long id);

    List<Article> findByTag(String tag);

    Long likeArticle(Long id) throws ArticleNotExist;

    Long dislikeArticle(Long id) throws ArticleNotExist;

    Long incrementViews(Long id) throws ArticleNotExist;

    Page<Article> findPopular(int page, int pageSize);

    Page<Article> findMostLiked(int page, int pageSize);

    Page<Article> findAllSortedByDateAsc(Pageable pageable);

    Page<Article> findAllSortedByDateAscNewerThan(Date addedDate, Pageable pageable);
}

