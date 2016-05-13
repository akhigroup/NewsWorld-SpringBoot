package pl.pwr.news.webapp.controller.user.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.model.user.Gender;
import pl.pwr.news.model.user.User;
import pl.pwr.news.model.usertag.UserTag;
import pl.pwr.news.webapp.controller.tag.dto.ListedTagDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by jakub on 3/9/16.
 */
@Getter
@Setter
public class UserDTO {

    private String token;
    private String firstName;
    private String lastName;
    private Gender gender;
    private List<ListedTagDTO> tags = new ArrayList<>();

    public UserDTO(User user) {
        if (user != null) {
            this.token = user.getToken();
            this.firstName = user.getFirstname();
            this.lastName = user.getLastname();
            this.gender = user.getGender();
            Set<UserTag> tags = user.getTags();
            tags.forEach(userTag -> this.tags.add(new ListedTagDTO(userTag.getTag())));
        }
    }
}