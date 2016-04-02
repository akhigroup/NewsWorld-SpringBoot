package pl.pwr.news.webapp.controller.article;
//TODO: zmiana na DTO, UPDATE

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.service.article.ArticleService;
import pl.pwr.news.service.category.CategoryService;
import pl.pwr.news.service.tag.TagService;
import pl.pwr.news.webapp.controller.Response;
import pl.pwr.news.webapp.controller.article.dto.ArticleDTO;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.common.lang3.StringUtils.isNotBlank;

/**
 * Created by jakub on 2/29/16.
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class ArticleApi {

    private final int MAX_PAGE_SIZE = 100;
    private final String DEFAULT_PAGE_SIZE = "20";
    private final String DEFAULT_START_PAGE = "0";

    @Autowired
    ArticleService articleService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    TagService tagService;

    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public Response<List<ArticleDTO>> getArticles(@RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                                  @RequestParam(required = false, defaultValue = DEFAULT_START_PAGE) int page) {
        pageSize = getCorrectMaxPageSize(pageSize);
        Page<Article> databaseArticle = articleService.findAll(new PageRequest(page, pageSize));
        List<ArticleDTO> articleDTOList = ArticleDTO.getList(databaseArticle.getContent());
        return new Response<>(articleDTOList);
    }


    @RequestMapping(value = "/article/popular", method = RequestMethod.GET)
    public Response<List<ArticleDTO>> getPopularArticles(@RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                                         @RequestParam(required = false, defaultValue = DEFAULT_START_PAGE) int page) {
        pageSize = getCorrectMaxPageSize(pageSize);
        Page<Article> databaseArticle = articleService.findPopular(page, pageSize);
        List<ArticleDTO> articleDTOList = ArticleDTO.getList(databaseArticle.getContent());
        return new Response<>(articleDTOList);
    }


    @RequestMapping(value = "/article/liked", method = RequestMethod.GET)
    public Response<List<ArticleDTO>> getMostLikedArticles(@RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                                           @RequestParam(required = false, defaultValue = DEFAULT_START_PAGE) int page) {
        pageSize = getCorrectMaxPageSize(pageSize);
        Page<Article> databaseArticle = articleService.findMostLiked(page, pageSize);
        List<ArticleDTO> articleDTOList = ArticleDTO.getList(databaseArticle.getContent());
        return new Response<>(articleDTOList);
    }

    private int getCorrectMaxPageSize(int pageSize) {
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        return pageSize;
    }


    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET)
    public Response<Article> getArticle(@PathVariable("articleId") Long articleId) {
        articleService.incrementViews(articleId);
        return new Response<>(articleService.findById(articleId));
    }

    @RequestMapping(value = "/article/{articleId}/like", method = RequestMethod.GET)
    public Response<Long> likeArticle(@PathVariable("articleId") Long articleId) {
        return new Response<>(articleService.likeArticle(articleId));
    }

    @RequestMapping(value = "/article/{articleId}/dislike", method = RequestMethod.GET)
    public Response<Long> dislikeArticle(@PathVariable("articleId") Long articleId) {
        return new Response<>(articleService.dislikeArticle(articleId));
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
            @RequestParam Long[] categoryIds,
            @RequestParam Long[] tagIds) {
        List<Category> categories = categoryService.findAll(Arrays.asList(categoryIds));
        List<Tag> tags = tagService.findAll(Arrays.asList(tagIds));
        Article article = Article.builder().title(title).text(text).imageUrl(imageUrl).
                link(link).categories(categories).tags(tags).addedDate(new Date()).build();
        articleService.createOrUpdate(article);

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
        return new Response<>(articleService.findAll(keyword, ""));
    }
}
