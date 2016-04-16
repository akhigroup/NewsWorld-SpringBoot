package pl.pwr.news.webapp.controller.category.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.webapp.controller.article.dto.ListedArticleDTO;
import pl.pwr.news.webapp.controller.tag.dto.ListedTagDTO;

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
    private List<ListedTagDTO> tags = new ArrayList<>();
    private List<ListedArticleDTO> articles = new ArrayList<>();

    public CategoryDTO(Category category) {
        if (category != null) {
            this.id = category.getId();
            this.imageUrl = category.getImageUrl();
            this.name = category.getName();
            Set<Tag> tags = category.getTags();
            tags.forEach(tag -> this.tags.add(new ListedTagDTO(tag)));
            Set<Article> articles = category.getArticles();
            articles.forEach(article -> this.articles.add(new ListedArticleDTO(article)));
        }
    }

    public static List<CategoryDTO> getList(List<Category> categories) {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        categories.forEach(category -> categoryDTOList.add(new CategoryDTO(category)));

        return categoryDTOList;
    }
}

