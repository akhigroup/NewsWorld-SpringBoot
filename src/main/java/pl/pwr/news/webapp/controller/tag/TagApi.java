package pl.pwr.news.webapp.controller.tag;
//TODO: zmiana na DTO, UPDATE
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pwr.news.factory.TagFactory;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.service.category.CategoryService;
import pl.pwr.news.service.tag.TagService;
import pl.pwr.news.webapp.controller.Response;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jakub on 3/9/16.
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class TagApi {

    @Autowired
    TagService tagService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    TagFactory tagFactory;

    @RequestMapping(value = "/tag", method = RequestMethod.GET)
    public Response<List<Tag>> getTags() {
        return new Response<>(tagService.findAll());
    }

    @RequestMapping(value = "/tag/{tagId}", method = RequestMethod.GET)
    public Response<Tag> getTag(@PathVariable("tagId") Long tagId) {

        if (!tagService.exist(tagId)) {
            return new Response<>("-1", "Tag not found");
        }

        return new Response<>(tagService.findById(tagId));
    }

    @RequestMapping(value = "/tag", method = RequestMethod.POST)
    public Response<Tag> saveTag(@RequestParam String name,
                                 @RequestParam(required = false, defaultValue = "0") Long[] categoryIds) {
        List<Category> categories = categoryService.findAll(Arrays.asList(categoryIds));
        Tag tag = tagFactory.getInstance(name, categories);
        tagService.createTag(tag);
        return new Response<>(tag);
    }
}
