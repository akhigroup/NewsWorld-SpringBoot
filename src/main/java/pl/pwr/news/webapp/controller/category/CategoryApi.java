package pl.pwr.news.webapp.controller.category;
//TODO: zmiana na DTO, UPDATE
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pwr.news.factory.CategoryFactory;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.service.category.CategoryService;
import pl.pwr.news.service.tag.TagService;
import pl.pwr.news.webapp.controller.Response;

import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * Created by jakub on 3/9/16.
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class CategoryApi {

    @Autowired
    CategoryService categoryService;

    @Autowired
    TagService tagService;

    @Autowired
    CategoryFactory categoryFactory;

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public Response<List<Category>> getCategories() {
        return new Response<>(categoryService.findAll());
    }

    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    public Response<Category> getCategory(@PathVariable("categoryId") Long categoryId) {

        boolean categoryNotExist = !categoryService.exist(categoryId);
        if (categoryNotExist) {
            return new Response<>("-1", "Category not found");
        }

        return new Response<>(categoryService.findById(categoryId));
    }

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public Response<Category> saveCategory(
            @RequestParam String name,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(required = false, defaultValue = "0") Long[] tagIds) {
        List<Tag> tags = tagService.findAll(Arrays.asList(tagIds));
        Category category = categoryFactory.getInstance(name, imageUrl, tags);
        categoryService.createCategory(category);
        return new Response<>(category);
    }

    @RequestMapping(value = "/category", method = RequestMethod.PUT)
    public Response<Category> updateCategory(
            @RequestParam Long categoryId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String imageUrl) {

        boolean categoryNotExist = !categoryService.exist(categoryId);
        if (categoryNotExist) {
            return new Response<>("-1", "Category not found");
        }

        Category category = categoryService.findById(categoryId);

        if (isNotBlank(name)) {
            category.setName(name);
        }

        if (isNotBlank(imageUrl)) {
            category.setImageUrl(imageUrl);
        }

        categoryService.updateCategory(category);
        return new Response<>(category);
    }
}
