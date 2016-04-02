package pl.pwr.news.webapp.controller.tag.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.webapp.controller.article.dto.ArticleDTO;
import pl.pwr.news.webapp.controller.article.dto.ListedArticleDTO;
import pl.pwr.news.webapp.controller.category.dto.CategoryDTO;
import pl.pwr.news.webapp.controller.category.dto.ListedCategoryDTO;

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
    private ListedCategoryDTO category;
    private List<ListedArticleDTO> articles = new ArrayList<>();

    public TagDTO(Tag tag) {
        if (tag != null) {
            this.id = tag.getId();
            this.name = tag.getName();
            this.category = new ListedCategoryDTO(tag.getCategory());
            if (this.category.getId() == null) {
                this.category = null;
            }
            Set<Article> articles = tag.getArticles();
            articles.forEach(article -> this.articles.add(new ListedArticleDTO(article)));
        }
    }

    public static List<TagDTO> getList(List<Tag> tags) {
        List<TagDTO> tagDTOList = new ArrayList<>();
        tags.forEach(tag -> tagDTOList.add(new TagDTO(tag)));

        return tagDTOList;
    }
}
