package pl.pwr.news.webapp.controller.user;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.user.User;
import pl.pwr.news.service.article.ArticleNotExist;
import pl.pwr.news.service.article.ArticleService;
import pl.pwr.news.service.user.UserNotExist;
import pl.pwr.news.service.user.UserService;
import pl.pwr.news.webapp.controller.Response;
import pl.pwr.news.webapp.controller.article.dto.ArticleDTO;

import java.util.Collections;
import java.util.List;

/**
 * Created by jakub on 2/29/16.
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
@Log4j
public class UserApi {

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;


    @RequestMapping(value = "/user/favourite/{articleId}", method = RequestMethod.PUT)
    public Response<User> addFavouriteArticle(@PathVariable Long articleId,
                                              @RequestParam String userToken) {

        try {
            userService.addFavouriteArticle(articleId, userToken);
        } catch (UserNotExist | ArticleNotExist exception) {
            String exceptionMessage = exception.getMessage();
            log.warn(exceptionMessage);
            return new Response<>("-1", exceptionMessage);
        }

        return new Response<>();
    }

    @RequestMapping(value = "/user/favourite", method = RequestMethod.GET)
    public Response<List<ArticleDTO>> getFavourite(@RequestParam String userToken) {

        List<Article> articleList;
        try {
            articleList = userService.findAllFavouriteArticles(userToken);
        } catch (UserNotExist userNotExist) {
            log.warn(userNotExist.getMessage());
            articleList = Collections.emptyList();
        }
        List<ArticleDTO> articleDTOList = ArticleDTO.getList(articleList);
        return new Response<>(articleDTOList);
    }

}
