package pl.pwr.news.webapp.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pwr.news.model.user.User;
import pl.pwr.news.service.article.ArticleService;
import pl.pwr.news.service.category.CategoryService;
import pl.pwr.news.service.tag.TagService;
import pl.pwr.news.service.user.UserService;

/**
 * Created by Evelan-E6540 on 27.02.2016.
 */
@Controller
@Log4j
@RequestMapping(value = "/admin/")
public class HomeController {

    @Autowired
    ArticleService articleService;

    @Autowired
    TagService tagService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/")
    public String home(Model model) {
        Long allArticles = articleService.countAll();
        model.addAttribute("allArticles", allArticles);

        Long allTags = tagService.countAll();
        model.addAttribute("allTags", allTags);

        Long allCategories = categoryService.countAll();
        model.addAttribute("allCategories", allCategories);

        Long allUsers = userService.countAll();
        model.addAttribute("allUsers", allUsers);

        User user = new User();
        user.setEmail("rafal2@rafal.com");
        user.setUsername("user2");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode("password"));
        userService.save(user);

        return "index";
    }

}
