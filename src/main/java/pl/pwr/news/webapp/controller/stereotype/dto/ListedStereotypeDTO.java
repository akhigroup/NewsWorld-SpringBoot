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
    private String name;

    public ListedStereotypeDTO(Stereotype stereotype) {
        if (stereotype != null) {
            this.id = stereotype.getId();
            this.name = stereotype.getName();
        }
    }

    public static List<ListedStereotypeDTO> getList(List<Stereotype> stereotypes) {
        List<ListedStereotypeDTO> stereotypeDTOList = new ArrayList<>();
        stereotypes.forEach(stereotype -> stereotypeDTOList.add(new ListedStereotypeDTO(stereotype)));

        return stereotypeDTOList;
    }
}
