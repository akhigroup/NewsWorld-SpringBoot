package pl.pwr.news.webapp.controller.admin;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pwr.news.model.user.User;
import pl.pwr.news.service.user.UserService;

import java.util.List;

/**
 * Created by jakub on 3/22/16.
 *
 */
@Controller
@RequestMapping(value = "/admin/user/")
@Log4j
public class AdminController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/list")
    public String listUsers(Model model) {

        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);

        return "user/list";
    }


}
