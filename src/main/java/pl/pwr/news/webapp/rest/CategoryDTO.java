package pl.pwr.news.webapp.rest;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.category.Category;

/**
 * Created by jakub on 3/9/16.
 */
@Getter
@Setter
public class CategoryDTO {

    private Long id;
    private String imageUrl;
    private String name;

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.imageUrl = category.getImageUrl();
        this.name = category.getName();
    }
}
