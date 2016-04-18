package pl.pwr.news.service.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.service.category.CategoryNotExist;
import pl.pwr.news.service.tag.TagNotExist;

import java.util.List;

/**
 * Created by jakub on 2/29/16.
 */
@Service
public interface ArticleService {

    List<Article> findAll(String keyword, String link);

    void addCategory(Long articleId, Long... categoryIds) throws ArticleNotExist, CategoryNotExist;

    void addTag(Long articleId, Long... tagId) throws ArticleNotExist, TagNotExist;

    void removeTag(Long articleId, Long... tagId) throws ArticleNotExist, TagNotExist;

    Article create(Article article) throws NotUniqueArticle;

    Article update(Article article) throws ArticleNotExist;

    List<Article> findAll();

    boolean unique(String link);

    boolean exist(Long id) throws ArticleNotExist;

    Long countAll();

    Page<Article> findAll(Pageable pageable);

    Article findById(Long id) throws ArticleNotExist;

    List<Article> findByTag(String tag);

    Long likeArticle(Long id) throws ArticleNotExist;

    Long dislikeArticle(Long id) throws ArticleNotExist;

    Long incrementViews(Long id) throws ArticleNotExist;

    Page<Article> findPopular(int page, int pageSize);

    Page<Article> findMostLiked(int page, int pageSize);
}
