package pl.pwr.news.webapp.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.service.article.ArticleService;

import java.util.List;

/**
 * Created by jakub on 2/29/16.
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @JsonProperty(required = false)
    @ApiModelProperty(notes = "Article list")
    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public List<Article> getArticles() {
        return articleService.findAll();
    }

    @JsonProperty(required = false)
    @ApiModelProperty(notes = "Create article")
    @RequestMapping(value = "/article", method = RequestMethod.POST)
    public void saveArticle(
            @RequestParam("title") String title,
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "imageUrl", required = false) String imageUrl,
            @RequestParam(value = "link") String link) {


        Article entity = new Article();
        entity.setTitle(title);
        entity.setText(text);
        entity.setImageUrl(imageUrl);
        entity.setLink(link);

        articleService.save(entity);
    }

}
