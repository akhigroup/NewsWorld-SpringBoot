package pl.pwr.news.webapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.service.tag.TagService;

import java.util.List;

/**
 * Created by jakub on 3/9/16.
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class TagController {

    @Autowired
    TagService tagService;

    @RequestMapping(value = "/tag", method = RequestMethod.GET)
    public Response<List<Tag>> getTags() {
        return new Response<>(tagService.findAll());
    }

    @RequestMapping(value = "/tag/{tagId}", method = RequestMethod.GET)
    public Response<Tag> getTag(@PathVariable("tagId") Long tagId) {

        boolean tagNotExist = !tagService.exist(tagId);
        if (tagNotExist) {
            return new Response<>("-1", "Tag not found");
        }

        return new Response<>(tagService.findById(tagId));
    }

    @RequestMapping(value = "/tag", method = RequestMethod.POST)
    public Response<Tag> saveTag(@RequestParam String name) {

        Tag tag = tagService.createTag(name);
        return new Response<>(tag);
    }
}
