package pl.pwr.news.webapp.controller.article.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.article.Article;

import java.util.Set;

/**
 * Created by jf on 4/2/16.
 */
@Getter
@Setter
public class ListedArticleDTO {

    private Long id;
    private String title;
    private String text;
    private String imageUrl;
    private String link;

    public ListedArticleDTO(Article article) {
        if (article != null) {
            this.id = article.getId();
            this.title = article.getTitle();
            this.text = article.getText();
            this.imageUrl = article.getImageUrl();
            this.link = article.getLink();
        }
    }
}
