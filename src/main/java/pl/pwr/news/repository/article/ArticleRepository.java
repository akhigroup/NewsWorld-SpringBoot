package pl.pwr.news.repository.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.news.model.article.Article;

import java.util.Date;
import java.util.List;

/**
 * Created by jakub on 2/29/16.
 */
@Repository
public interface ArticleRepository extends CrudRepository<Article, Long>, JpaSpecificationExecutor<Article>, PagingAndSortingRepository<Article, Long> {

    List<Article> findByTags_Name(String name);

    Page<Article> findAllByCategory_Id(Long categoryId, Pageable pageable);

    Article findByLink(String link);

    Page<Article> findAllByOrderByAddedDateAsc(Pageable pageable);

    Page<Article> findAllByAddedDateBeforeOrderByAddedDateAsc(Date addedDate, Pageable pageable);

    Page<Article> findAllByCategory_IdOrderByAddedDateAsc(Long categoryId, Pageable pageable);

    Page<Article> findAllByAddedDateBeforeAndCategory_IdOrderByAddedDateAsc(Date addedDate, Long categoryId, Pageable pageable);
}
