package pl.pwr.news.service.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.article.Article;

import java.util.List;

/**
 * Created by jakub on 2/29/16.
 */
@Service
public interface ArticleService {

    List<Article> findAll(String keyword, String link);

    void addCategory(Long articleId, Long... categoryId);

    void addTag(Long articleId, Long... tagId);

    Article createOrUpdate(Article entity);

    List<Article> findAll();

    Page<Article> findAll(Pageable pageable);

    Article findById(Long id);

    List<Article> findByTag(String tag);

}
