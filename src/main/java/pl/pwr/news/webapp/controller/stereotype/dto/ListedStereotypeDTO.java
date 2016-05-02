package pl.pwr.news.webapp.controller.stereotype.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.stereotype.Stereotype;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 4/2/16.
 */
@Getter
@Setter
public class ListedStereotypeDTO {

    private Long id;
    private String imageUrl;
    private String name;

    public ListedStereotypeDTO(Stereotype stereotype) {
        if (stereotype != null) {
            this.id = stereotype.getId();
            this.imageUrl = stereotype.getImageUrl();
            this.name = stereotype.getName();
        }
    }

    public static List<ListedStereotypeDTO> getList(List<Stereotype> categories) {
        List<ListedStereotypeDTO> stereotypeDTOList = new ArrayList<>();
        categories.forEach(stereotype -> stereotypeDTOList.add(new ListedStereotypeDTO(stereotype)));

        return stereotypeDTOList;
    }
}
