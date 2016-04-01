package pl.pwr.news.webapp.controller.article.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.webapp.controller.category.dto.CategoryDTO;
import pl.pwr.news.webapp.controller.tag.dto.TagDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by jakub on 3/9/16.
 */
@Getter
@Setter
public class ArticleDTO {

    private String title;
    private String text;
    private String imageUrl;
    private String link;
    private List<TagDTO> tags = new ArrayList<>();
    private List<CategoryDTO> categories = new ArrayList<>();
    private Date addedDate;

    public ArticleDTO(Article article) {
        this.title = article.getTitle();
        this.text = article.getText();
        this.imageUrl = article.getImageUrl();
        this.link = article.getLink();
        Set<Tag> tags = article.getTags();
        tags.forEach(tag -> this.tags.add(new TagDTO(tag)));
        Set<Category> categories = article.getCategories();
        categories.forEach(category -> this.categories.add(new CategoryDTO(category)));
        this.addedDate = article.getAddedDate();
    }

    public static List<ArticleDTO> getList(List<Article> articles) {
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        articles.forEach(article -> articleDTOList.add(new ArticleDTO(article)));
        return articleDTOList;
    }
}
