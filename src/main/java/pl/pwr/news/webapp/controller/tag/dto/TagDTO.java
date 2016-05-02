package pl.pwr.news.webapp.controller.tag.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.webapp.controller.article.dto.ListedArticleDTO;
import pl.pwr.news.webapp.controller.stereotype.dto.ListedStereotypeDTO;

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
    private ListedStereotypeDTO stereotype;
    private List<ListedArticleDTO> articles = new ArrayList<>();

    public TagDTO(Tag tag) {
        if (tag != null) {
            this.id = tag.getId();
            this.name = tag.getName();
            this.stereotype = new ListedStereotypeDTO(tag.getStereotype());
            if (this.stereotype.getId() == null) {
                this.stereotype = null;
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
