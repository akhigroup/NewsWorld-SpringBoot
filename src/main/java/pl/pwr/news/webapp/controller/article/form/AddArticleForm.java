package pl.pwr.news.webapp.controller.article.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pwr.news.model.article.Article;

import java.io.Serializable;

/**
 * Created by jakub on 4/2/16.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddArticleForm implements Serializable {

    Article article = new Article();
    String tagsInString = new String();
    Long categoryId = -1L;

    public AddArticleForm(Article article) {
        this.article = article;
    }

}
