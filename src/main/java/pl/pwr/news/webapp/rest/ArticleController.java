package pl.pwr.news.webapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.service.article.ArticleService;

import javax.servlet.http.HttpServletResponse;
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
    public Response<List<ArticleDTO>> getArticles(@RequestParam(required = false, defaultValue = "20") int pageSize,
                                                  @RequestParam(required = false, defaultValue = "0") int page,
                                                  HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");

        if (pageSize > 100) {
            pageSize = 100;
        }

        Page<Article> databaseArticle = articleService.findAll(new PageRequest(page, pageSize));
        List<ArticleDTO> articleDTOList = ArticleDTO.getList(databaseArticle.getContent());
        return new Response<>(articleDTOList);
    }

    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET)
    public Response<Article> getArticle(@PathVariable("articleId") Long articleId) {
        return new Response<>(articleService.findById(articleId));
    }

    @RequestMapping(value = "/article/search", method = RequestMethod.GET)
    public Response<List<ArticleDTO>> searchArticles(@RequestParam(value = "tag") String tag) {
        List<ArticleDTO> articleDTOList = ArticleDTO.getList(articleService.findByTag(tag));
        return new Response<>(articleDTOList);
    }

    @RequestMapping(value = "/article", method = RequestMethod.POST)
    public Response<Article> saveArticle(
            @RequestParam("title") String title,
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "imageUrl", required = false) String imageUrl,
            @RequestParam(value = "link") String link,
            @RequestParam(required = false) Long[] categoryIds,
            @RequestParam(required = false) Long[] tagIds) {

        Article article = new Article();
        article.setTitle(title);
        article.setText(text);
        article.setImageUrl(imageUrl);
        article.setLink(link);

        article = articleService.createOrUpdate(article);

        Long articleId = article.getId();
        if (categoryIds != null) {
            articleService.addCategory(articleId, categoryIds);
        }

        if (tagIds != null) {
            articleService.addTag(articleId, tagIds);
        }

        return new Response<>(article);
    }

    @RequestMapping(value = "/article", method = RequestMethod.PUT)
    public Response<Article> updateArticle(
            @RequestParam Long articleId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String text,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(required = false) String link,
            @RequestParam(required = false) Long[] categoryIds,
            @RequestParam(required = false) Long[] tagIds) {

        Article article = articleService.findById(articleId);

        if (article == null) {
            return new Response<>("-1", "No article with id: " + articleId + " to update", null);
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

        articleService.createOrUpdate(article);

        if (categoryIds != null) {
            articleService.addCategory(articleId, categoryIds);
        }

        if (tagIds != null) {
            articleService.addTag(articleId, tagIds);
        }

        article = articleService.findById(articleId);
        return new Response<>(article);
    }

    @RequestMapping(value = "/article/search/", method = RequestMethod.GET)
    public Response<List<Article>> searchArticle(
            @RequestParam(value = "keyword", required = false) String keyword) {
        //TODO - dorobic cale filtrowanie
        return new Response<>(articleService.findAll(keyword, ""));
    }
}
