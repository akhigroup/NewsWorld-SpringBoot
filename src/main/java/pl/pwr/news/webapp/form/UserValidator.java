package pl.pwr.news.webapp.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.pwr.news.service.MessageService;
import pl.pwr.news.service.UserService;
import pl.pwr.news.service.UserServiceImpl;

/**
 * Created by Rafal on 2016-02-29.
 */
@Component
public class UserValidator implements Validator {



    @Autowired
    private MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserForm form = (UserForm) target;
        validatePasswords(errors, form);
        validateEmail(errors, form);
        validateUsername(errors, form);
    }

    private void validatePasswords(Errors errors, UserForm form) {
        if (!form.getPassword().equals(form.getPasswordConfirmation())) {
            errors.rejectValue("passwordConfirmation", "password.no_match", messageService.getMessage("register.error.match"));
        }
    }

    private void validateEmail(Errors errors, UserForm form) {
        if (userService.findByEmail(form.getEmail())  != null ){
            errors.rejectValue("email", "email.exists", messageService.getMessage("register.error.emailExists"));
        }
    }

    private void validateUsername(Errors errors, UserForm form) {
        if (userService.findByUsername(form.getUsername()) != null) {
            errors.rejectValue("username", "username.exists", messageService.getMessage("register.error.usernameExists"));
        }
    }
}