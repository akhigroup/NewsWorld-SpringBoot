package pl.pwr.news.webapp.controller.user.dto;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.user.Gender;
import pl.pwr.news.model.user.User;

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

    public UserDTO(User user) {
        if (user != null) {
            this.token = user.getToken();
            this.firstName = user.getFirstname();
            this.lastName = user.getLastname();
            this.gender = user.getGender();
        }
    }
}