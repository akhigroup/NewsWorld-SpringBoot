package pl.pwr.news.webapp.controller.tag.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.webapp.controller.article.dto.ArticleDTO;
import pl.pwr.news.webapp.controller.category.dto.CategoryDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by jakub on 3/9/16.
 */
@Getter
@Setter
public class TagDTO {

    private Long id;
    private String name;
    private CategoryDTO category;
    private List<ArticleDTO> articles = new ArrayList<>();

    public TagDTO(Tag tag) {
        if (tag != null) {
            this.id = tag.getId();
            this.name = tag.getName();
            this.category = new CategoryDTO(tag.getCategory());
            if (this.category.getId() == null) {
                this.category = null;
            }
            Set<Article> articles = tag.getArticles();
            articles.forEach(article -> this.articles.add(new ArticleDTO(article)));
        }
    }
}