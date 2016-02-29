package pl.pwr.news.webapp.form;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.user.UserRole;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Rafal on 2016-02-29.
 */
@Getter
@Setter
public class UserForm {

    public static final String PASSWORD_REGEX_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{5,12}";
    public static final String EMAIL_REGEX_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String PASSWORD_ERROR_MESSAGE = "Password must be at least 5 characters long and contain at least one uppercase letter, "
            + "one lowercase letter, one number and one special character.";

    @Size(min = 4, message = "Username is too short.")
    private String username;

    @Pattern(regexp = EMAIL_REGEX_PATTERN, message="Provide a valid email.")
    private String email;

    @Pattern(regexp = PASSWORD_REGEX_PATTERN, message = PASSWORD_ERROR_MESSAGE)
    private String password;

    private String passwordConfirmation;


    private String publicKey;

}