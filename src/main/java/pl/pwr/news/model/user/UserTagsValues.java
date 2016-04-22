package pl.pwr.news.model.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.TreeMap;


/**
 * Created by Jasiek on 18.04.2016.
 */

@Getter
@Setter
public class UserTagsValues {

    @Id
    private Long id;

    private Long sumOfValues;

    private TreeMap<Long, Long> tags;  //tagId : tagvalue
}
