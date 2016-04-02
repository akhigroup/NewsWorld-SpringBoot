package pl.pwr.news.webapp.controller.tag.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.tag.Tag;
import java.util.Set;

/**
 * Created by jf on 4/2/16.
 */
@Getter
@Setter
public class ListedTagDTO {

    private Long id;
    private String name;

    public ListedTagDTO(Tag tag) {
        if (tag != null) {
            this.id = tag.getId();
            this.name = tag.getName();
        }
    }
}
