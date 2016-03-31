package pl.pwr.news.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pwr.news.model.user.User;
import pl.pwr.news.model.user.UserRole;

import javax.ws.rs.GET;

/**
 * Created by Rafal on 2016-03-29.
 */

@Controller
public class LoginController {

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String showLoginPage(Model model){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User login(){
        User user = new User();
        user.setEmail("rafal@rafal.com");
        user.setPassword("HASZłO");
        user.setRole(UserRole.ADMIN);
        return user;
    }

}
