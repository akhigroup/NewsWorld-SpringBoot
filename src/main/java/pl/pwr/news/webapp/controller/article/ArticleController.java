package pl.pwr.news.webapp.controller.article;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.service.article.ArticleService;
import pl.pwr.news.service.category.CategoryService;
import pl.pwr.news.service.tag.TagService;

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

    @Autowired
    TagService tagService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/list")
    public String listArticles(Model model) {

        List<Article> articleList = articleService.findAll();
        model.addAttribute("articleList", articleList);


        return "article/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String createNewArticle(Model model) {
        List<Tag> tagList = tagService.findAll();
        List<Category> categoryList = categoryService.findAll();

        model.addAttribute("tagList", tagList);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("newArticle", new Article());
        return "article/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveNewArticle(@ModelAttribute(value = "newArticle") Article article) {

        articleService.createOrUpdate(article);

        return "redirect:/admin/article/add";
    }

}
