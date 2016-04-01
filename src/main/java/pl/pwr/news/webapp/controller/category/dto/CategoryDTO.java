package pl.pwr.news.webapp.controller.category.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.webapp.controller.article.dto.ArticleDTO;
import pl.pwr.news.webapp.controller.tag.dto.TagDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by jakub on 3/9/16.
 */
@Getter
@Setter
public class CategoryDTO {

    private Long id;
    private String imageUrl;
    private String name;
    private List<TagDTO> tags = new ArrayList<>();
    private List<ArticleDTO> articles = new ArrayList<>();

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.imageUrl = category.getImageUrl();
        this.name = category.getName();
        Set<Tag> tags = category.getTags();
        tags.forEach(tag -> this.tags.add(new TagDTO(tag)));
        Set<Article> articles = category.getArticles();
        articles.forEach(article -> this.articles.add(new ArticleDTO(article)));
    }
}
