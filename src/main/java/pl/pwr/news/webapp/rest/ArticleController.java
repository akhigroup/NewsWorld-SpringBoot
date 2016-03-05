package pl.pwr.news.webapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.service.article.ArticleService;

import java.util.List;

import static org.elasticsearch.common.lang3.StringUtils.isNotBlank;

/**
 * Created by jakub on 2/29/16.
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public ResponseDTO<List<Article>> getArticles() {
        // Na potrzeby testów 40 elementów, bo zamula.
        return new ResponseDTO<>(articleService.findAll().subList(0, 40));
    }

    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET)
    public ResponseDTO<Article> getArticle(@PathVariable("articleId") Long articleId) {
        return new ResponseDTO<>(articleService.findById(articleId));
    }

    @RequestMapping(value = "/article", method = RequestMethod.POST)
    public ResponseDTO<Article> saveArticle(
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
        return new ResponseDTO<>(entity);
    }

    @RequestMapping(value = "/article", method = RequestMethod.PUT)
    public ResponseDTO<Article> updateArticle(
            @RequestParam("id") Long id,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "imageUrl", required = false) String imageUrl,
            @RequestParam(value = "link", required = false) String link) {

        Article article = articleService.findById(id);

        if (article == null) {
            return new ResponseDTO<>("-1", "No article with id: " + id + " to update", null);
        }

        if (isNotBlank(title)) {
            article.setTitle(title);
        }

        if (isNotBlank(text)) {
            article.setText(text);
        }

        if (isNotBlank(imageUrl)) {
            article.setImageUrl(imageUrl);
        }

        if (isNotBlank(link)) {
            article.setLink(link);
        }

        articleService.save(article);

        return new ResponseDTO<>(article);
    }

    @RequestMapping(value = "/article/search", method = RequestMethod.GET)
    public ResponseDTO<List<Article>> searchArticle(
            @RequestParam(value = "keyword", required = false) String keyword) {
        //TODO - dorobic cale filtrowanie
        return new ResponseDTO<>(articleService.findAll(keyword, ""));
    }

}
