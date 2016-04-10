package pl.pwr.news.webapp.controller.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pwr.news.factory.CategoryFactory;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.service.category.CategoryService;
import pl.pwr.news.webapp.controller.Response;
import pl.pwr.news.webapp.controller.article.dto.ArticleDTO;
import pl.pwr.news.webapp.controller.category.dto.CategoryDTO;
import pl.pwr.news.webapp.controller.category.dto.ListedCategoryDTO;

import java.util.ArrayList;
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
    CategoryFactory categoryFactory;

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public Response<List<ListedCategoryDTO>> getCategories() {

        List<Category> categoryList = categoryService.findAll();
        List<ListedCategoryDTO> categoryDTOList = ListedCategoryDTO.getList(categoryList);

        return new Response<>(categoryDTOList);
    }


    @RequestMapping(value = "/category/articles/{categoryId}", method = RequestMethod.GET)
    public Response<List<ArticleDTO>> getArticlesFromCategory(@PathVariable("categoryId") Long categoryId) {

        if (!categoryService.exist(categoryId)) {
            return new Response<>("-1", "Category not found");
        }
        Category category = categoryService.findById(categoryId);
        List<Article> articleList = new ArrayList<Article>(category.getArticles());
        List<ArticleDTO> articleDTOList = ArticleDTO.getList(articleList);
        return new Response<>(articleDTOList);
    }

    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    public Response<CategoryDTO> getCategory(@PathVariable("categoryId") Long categoryId) {

        if (!categoryService.exist(categoryId)) {
            return new Response<>("-1", "Category not found");
        }

        CategoryDTO categoryDTO = new CategoryDTO(categoryService.findById(categoryId));

        return new Response<>(categoryDTO);
    }

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public Response<Category> saveCategory(
            @RequestParam String name,
            @RequestParam(required = false) String imageUrl) {
        Category category = categoryFactory.getInstance(name, imageUrl);
        categoryService.createCategory(category);
        return new Response<>(category);
    }

    @RequestMapping(value = "/category", method = RequestMethod.PUT)
    public Response<Category> updateCategory(
            @RequestParam Long categoryId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String imageUrl) {

        if (!categoryService.exist(categoryId)) {
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
