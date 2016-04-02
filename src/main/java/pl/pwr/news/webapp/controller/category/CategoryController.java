package pl.pwr.news.webapp.controller.category;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.service.category.CategoryService;

import java.util.List;

/**
 * Created by jakub on 3/22/16.
 */
@Controller
@RequestMapping(value = "/admin/category/")
@Log4j
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/list")
    public String listCategories(Model model) {
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);

        return "category/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String createNewCategory(Model model) {
        model.addAttribute("newCategory", new Category());

        return "category/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveNewCategory(@ModelAttribute(value = "newCategory") Category category) {
        categoryService.createCategory(category);

        return "redirect:/admin/category/add";
    }
}
