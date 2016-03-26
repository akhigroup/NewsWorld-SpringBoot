package pl.pwr.news.webapp.controller.user.form;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Rafal on 2016-03-26.
 */
@Getter
@Setter
public class RegisterRequestBody {

    private String mail;
    private String username;
    private String password;
    private String confirmPassword;
}
