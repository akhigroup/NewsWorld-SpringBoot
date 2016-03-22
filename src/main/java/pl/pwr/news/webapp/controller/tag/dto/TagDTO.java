package pl.pwr.news.webapp.controller.tag.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.tag.Tag;

/**
 * Created by jakub on 3/9/16.
 */
@Getter
@Setter
public class TagDTO {

    private Long id;
    private String name;

    public TagDTO(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }

}
