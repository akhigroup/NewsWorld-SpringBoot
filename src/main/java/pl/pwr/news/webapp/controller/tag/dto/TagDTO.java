package pl.pwr.news.webapp.controller.tag.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.category.Category;
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
    private List<CategoryDTO> categories = new ArrayList<>();
    private List<ArticleDTO> articles = new ArrayList<>();

    public TagDTO(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
        Set<Category> categories = tag.getCategories();
        categories.forEach(category -> this.categories.add(new CategoryDTO(category)));
        Set<Article> articles = tag.getArticles();
        articles.forEach(article -> this.articles.add(new ArticleDTO(article)));
    }
}
