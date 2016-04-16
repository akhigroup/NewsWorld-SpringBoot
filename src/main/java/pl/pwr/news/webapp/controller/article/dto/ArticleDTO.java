package pl.pwr.news.webapp.controller.article.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.webapp.controller.category.dto.ListedCategoryDTO;
import pl.pwr.news.webapp.controller.tag.dto.ListedTagDTO;

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

    private Long id;
    private String title;
    private String text;
    private String imageUrl;
    private String link;
    private List<ListedTagDTO> tags = new ArrayList<>();
    private ListedCategoryDTO category;
    private Date addedDate;

    public ArticleDTO(Article article) {
        if (article != null) {
            this.id = article.getId();
            this.title = article.getTitle();
            this.text = article.getText();
            this.imageUrl = article.getImageUrl();
            this.link = article.getLink();
            Set<Tag> tags = article.getTags();
            tags.forEach(tag -> this.tags.add(new ListedTagDTO(tag)));
            Category category = article.getCategory();
            this.category = new ListedCategoryDTO(category);
            if ( this.category.getId() == null) {
                this.category = null;
            }
            this.addedDate = article.getAddedDate();
        }
    }

    public static List<ArticleDTO> getList(List<Article> articles) {
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        articles.forEach(article -> articleDTOList.add(new ArticleDTO(article)));

        return articleDTOList;
    }
}
//TODO: porządek, interfejs na getList (?)
