package pl.pwr.news.repository.article;

import org.springframework.data.jpa.domain.Specification;
import pl.pwr.news.model.article.Article;

/**
 * Created by jakub on 3/3/16.
 */
public class ArticleSpecification {

    public static Specification<Article> keywordInTitle(String keyword) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + keyword + "%");
    }

    public static Specification<Article> keywordInText(String keyword) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("text"), "%" + keyword + "%");
    }

    public static Specification<Article> linkLike(String link) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("link"), "%" + link + "%");
    }
}
