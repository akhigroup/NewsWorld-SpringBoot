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
 * Created by jf on 4/2/16.
 */
@Getter
@Setter
public class ListedCategoryDTO {

    private Long id;
    private String imageUrl;
    private String name;

    public ListedCategoryDTO(Category category) {
        if (category != null) {
            this.id = category.getId();
            this.imageUrl = category.getImageUrl();
            this.name = category.getName();
        }
    }

    public static List<ListedCategoryDTO> getList(List<Category> categories) {
        List<ListedCategoryDTO> categoryDTOList = new ArrayList<>();
        categories.forEach(category -> categoryDTOList.add(new ListedCategoryDTO(category)));

        return categoryDTOList;
    }
}
