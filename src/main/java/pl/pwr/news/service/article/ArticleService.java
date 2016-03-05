package pl.pwr.news.service.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.service.CrudOperations;

import java.util.List;

/**
 * Created by jakub on 2/29/16.
 */
@Service
public interface ArticleService extends CrudOperations<Article> {

    List<Article> findAll(String keyword, String link);

    Page<Article> findAll(Pageable pageable);

}
