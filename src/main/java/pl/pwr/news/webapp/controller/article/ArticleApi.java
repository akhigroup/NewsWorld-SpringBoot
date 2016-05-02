package pl.pwr.news.webapp.controller.article;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import pl.pwr.news.converter.DateConverter;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.stereotype.Stereotype;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.service.article.ArticleNotExist;
import pl.pwr.news.service.article.ArticleService;
import pl.pwr.news.service.stereotype.StereotypeService;
import pl.pwr.news.service.tag.TagService;
import pl.pwr.news.webapp.controller.Response;
import pl.pwr.news.webapp.controller.article.dto.ArticleDTO;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.common.lang3.StringUtils.isNotBlank;

/**
 * Created by jakub on 2/29/16.
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
@Log4j
public class ArticleApi {

    private final int MAX_PAGE_SIZE = 100;
    private final String DEFAULT_PAGE_SIZE = "20";
    private final String DEFAULT_START_PAGE = "0";

    @Autowired
    ArticleService articleService;

    @Autowired
    StereotypeService stereotypeService;

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

    @RequestMapping(value = "/article/date", method = RequestMethod.GET)
    public Response<List<ArticleDTO>> getArticlesSortedByDate(@RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                                              @RequestParam(required = false, defaultValue = DEFAULT_START_PAGE) int page) {
        pageSize = getCorrectMaxPageSize(pageSize);
        Page<Article> databaseArticle = articleService.findAllSortedByDateAsc(new PageRequest(page, pageSize));
        List<ArticleDTO> articleDTOList = ArticleDTO.getList(databaseArticle.getContent());
        return new Response<>(articleDTOList);
    }

    @RequestMapping(value = "/article/date/{addedDate}", method = RequestMethod.GET)
    public Response<List<ArticleDTO>> getArticlesSortedByDateNewerThan(
            @PathVariable("addedDate") String date,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(required = false, defaultValue = DEFAULT_START_PAGE) int page) {
        try {
            Date convertedDate = DateConverter.convertFromString(date);
            pageSize = getCorrectMaxPageSize(pageSize);
            Page<Article> databaseArticle = articleService.findAllSortedByDateAscNewerThan(convertedDate, new PageRequest(page, pageSize));
            List<ArticleDTO> articleDTOList = ArticleDTO.getList(databaseArticle.getContent());
            return new Response<>(articleDTOList);
        } catch (ParseException e) {
            return new Response<>("-1", e.getMessage() + " Valid date format: yyyy-MM-dd");
        }
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
    public Response<ArticleDTO> getArticle(@PathVariable("articleId") Long articleId) {
        try {
            articleService.incrementViews(articleId);
        } catch (ArticleNotExist articleNotExist) {
            log.error(articleNotExist.getMessage());
            //TODO - zwrócenie innej odpoweidzi na REST, jakiej?
        }
        ArticleDTO articleDTO = new ArticleDTO(articleService.findById(articleId));
        return new Response<>(articleDTO);
    }

    @RequestMapping(value = "/article/{articleId}/like", method = RequestMethod.GET)
    public Response<Long> likeArticle(@PathVariable("articleId") Long articleId) {
        try {
            return new Response<>(articleService.likeArticle(articleId));
        } catch (ArticleNotExist articleNotExist) {
            log.error(articleNotExist.getMessage());
            //TODO - zwrócenie innej odpoweidzi na REST, jakiej?
            return new Response<>(-1L);
        }
    }

    @RequestMapping(value = "/article/{articleId}/dislike", method = RequestMethod.GET)
    public Response<Long> dislikeArticle(@PathVariable("articleId") Long articleId) {
        try {
            return new Response<>(articleService.dislikeArticle(articleId));
        } catch (ArticleNotExist articleNotExist) {
            log.error(articleNotExist.getMessage());
            //TODO - zwrócenie innej odpoweidzi na REST, jakiej?
            return new Response<>(-1L);
        }
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
            @RequestParam Long stereotypeId,
            @RequestParam Long[] tagIds) {
        Stereotype stereotype = stereotypeService.findById(stereotypeId);
        List<Tag> tags = tagService.findAll(Arrays.asList(tagIds));
        Article article = Article.builder().title(title).text(text).imageUrl(imageUrl).
                link(link).stereotype(stereotype).tags(tags).addedDate(new Date()).build();
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
            @RequestParam(required = false) Long stereotypeId,
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

        if (stereotypeId != null) {
            Stereotype stereotype = stereotypeService.findById(stereotypeId);
            article.setStereotype(stereotype);
        }

        articleService.createOrUpdate(article);

        if (tagIds != null) {
            articleService.addTag(articleId, tagIds);
        }

        article = articleService.findById(articleId);
        return new Response<>(article);
    }

    @RequestMapping(value = "/article/search/", method = RequestMethod.GET)
    public Response<List<ArticleDTO>> searchArticle(
            @RequestParam(value = "keyword", required = false) String keyword) {

        List<Article> articleList = articleService.findAll(keyword, "");
        List<ArticleDTO> articleDTOList = ArticleDTO.getList(articleList);

        return new Response<>(articleDTOList);
    }
}
