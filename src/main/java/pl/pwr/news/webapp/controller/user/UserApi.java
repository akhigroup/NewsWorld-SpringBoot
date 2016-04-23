package pl.pwr.news.webapp.controller.user;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.pwr.news.model.user.User;
import pl.pwr.news.service.user.EmailNotUnique;
import pl.pwr.news.service.user.PasswordIncorrect;
import pl.pwr.news.service.user.UserNotExist;
import pl.pwr.news.service.user.UserService;
import pl.pwr.news.webapp.controller.Response;
import pl.pwr.news.webapp.controller.user.dto.UserDTO;

/**
 * Created by Evelan on 23/04/16.
 */
@Controller
@RequestMapping("/api")
@Log4j
public class UserApi {

    @Autowired
    UserService userService;

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public Response<UserDTO> register(@RequestParam String email,
                                      @RequestParam String password,
                                      @RequestParam String firstName,
                                      @RequestParam String lastName) {

        User registeredUser;
        try {
            registeredUser = userService.register(email, password, firstName, lastName);
        } catch (EmailNotUnique emailNotUnique) {
            String exceptionMessage = emailNotUnique.getMessage();
            log.warn(exceptionMessage);
            return new Response<>("-1", exceptionMessage);
        }

        UserDTO userDTO = new UserDTO(registeredUser);
        return new Response<>(userDTO);
    }


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public Response<UserDTO> login(@RequestParam String email,
                                   @RequestParam String password) {

        User loggedUser;
        try {
            loggedUser = userService.login(email, password);
        } catch (UserNotExist userNotExist) {
            String exceptionMessage = userNotExist.getMessage();
            log.warn(exceptionMessage);
            return new Response<>("-1", exceptionMessage);
        } catch (PasswordIncorrect passwordIncorrect) {
            String exceptionMessage = passwordIncorrect.getMessage();
            log.warn(exceptionMessage);
            return new Response<>("-2", exceptionMessage);
        }

        UserDTO userDTO = new UserDTO(loggedUser);
        return new Response<>(userDTO);
    }
}