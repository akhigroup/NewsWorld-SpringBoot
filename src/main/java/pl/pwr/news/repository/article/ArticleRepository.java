package pl.pwr.news.repository.article;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.news.model.article.Article;

import java.util.List;

/**
 * Created by jakub on 2/29/16.
 */
@Repository
public interface ArticleRepository extends CrudRepository<Article, Long>, JpaSpecificationExecutor<Article>, PagingAndSortingRepository<Article, Long> {


    List<Article> findByTags_Name(String name);
}
