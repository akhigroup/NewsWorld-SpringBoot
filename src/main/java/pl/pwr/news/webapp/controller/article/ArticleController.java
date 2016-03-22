package pl.pwr.news.webapp.controller.article;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.service.article.ArticleService;

import java.util.List;

/**
 * Created by jakub on 3/22/16.
 */
@Controller
@RequestMapping(value = "/admin/article/")
@Log4j
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/list")
    public String listArticles(Model model) {

        List<Article> articleList = articleService.findAll();
        model.addAttribute("articleList", articleList);


        return "article/list";
    }

}
