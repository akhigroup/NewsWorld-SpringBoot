package pl.pwr.news.webapp.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.pwr.news.model.user.User;
import pl.pwr.news.service.auth.TokenAuthenticationService;
import pl.pwr.news.service.user.UserService;

/**
 * Created by Rafal on 2016-03-26.
 */

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    TokenAuthenticationService tokenAuthenticationService;

    @RequestMapping(value = "/login/", method = RequestMethod.POST)
    public String login(){
       return "SUCCESS";
    }
}
