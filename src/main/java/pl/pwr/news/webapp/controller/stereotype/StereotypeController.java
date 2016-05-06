package pl.pwr.news.webapp.controller.stereotype;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.stereotype.Stereotype;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.service.article.ArticleService;
import pl.pwr.news.service.stereotype.StereotypeService;
import pl.pwr.news.service.tag.TagService;

import java.util.List;

/**
 * Created by jakub on 3/22/16.
 */
@Controller
@RequestMapping(value = "/admin/stereotype/")
@Log4j
public class StereotypeController {

    @Autowired
    StereotypeService stereotypeService;

    @Autowired
    TagService tagService;

    @RequestMapping(value = "/list")
    public String listStereotypes(Model model) {
        List<Stereotype> stereotypeList = stereotypeService.findAll();
        model.addAttribute("stereotypeList", stereotypeList);

        return "stereotype/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String createNewStereotype(Model model) {
        List<Tag> tagList = tagService.findAll();
        model.addAttribute("tagList", tagList);
        model.addAttribute("newStereotype", new Stereotype());

        return "stereotype/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveNewStereotype(@ModelAttribute(value = "newStereotype") Stereotype stereotype) {
        stereotypeService.createStereotype(stereotype);

        return "redirect:/admin/stereotype/add";
    }
}