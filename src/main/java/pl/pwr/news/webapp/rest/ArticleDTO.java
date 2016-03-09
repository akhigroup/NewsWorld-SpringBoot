package pl.pwr.news.webapp.rest;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.model.tag.Tag;

import java.util.ArrayList;
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
    private CategoryDTO category;
    private Long addedDate;


    public ArticleDTO(Article article) {
        this.title = article.getTitle();
        this.text = article.getText();
        this.imageUrl = article.getImageUrl();
        this.link = article.getLink();

        Set<Tag> tags = article.getTags();
        for (Tag tag : tags) {
            TagDTO tagDto = new TagDTO(tag);
            this.tags.add(tagDto);
        }

        Category category = article.getCategory();
        if (category != null) {
            this.category = new CategoryDTO(article.getCategory());
        }
        this.addedDate = article.getAddedDate();
    }

    public static List<ArticleDTO> getList(List<Article> articles) {
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        for (Article article : articles) {
            ArticleDTO articleDTO = new ArticleDTO(article);
            articleDTOList.add(articleDTO);
        }
        return articleDTOList;
    }
}
