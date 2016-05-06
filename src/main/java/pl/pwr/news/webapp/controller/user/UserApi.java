package pl.pwr.news.webapp.controller.user;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.user.User;
import pl.pwr.news.service.exception.ArticleNotExist;
import pl.pwr.news.service.exception.EmailNotUnique;
import pl.pwr.news.service.exception.PasswordIncorrect;
import pl.pwr.news.service.exception.UserNotExist;
import pl.pwr.news.service.user.UserService;
import pl.pwr.news.webapp.controller.Response;
import pl.pwr.news.webapp.controller.article.dto.ArticleDTO;
import pl.pwr.news.webapp.controller.user.dto.UserDTO;

import java.util.List;

/**
 * Created by Evelan on 23/04/16.
 */
@RestController
@RequestMapping("/api")
@Log4j
public class UserApi {

    @Autowired
    UserService userService;

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public Response<UserDTO> register(@RequestParam String email,
                                      @RequestParam String password,
                                      @RequestParam String firstName,
                                      @RequestParam String lastName) {

        User registeredUser;
        try {
            registeredUser = userService.register(email, password, firstName, lastName);
        } catch (EmailNotUnique emailNotUnique) {
            String exceptionMessage = emailNotUnique.getMessage();
            log.warn(exceptionMessage);
            return new Response<>("-1", exceptionMessage);
        }

        UserDTO userDTO = new UserDTO(registeredUser);
        return new Response<>(userDTO);
    }


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public Response<UserDTO> login(@RequestParam String email,
                                   @RequestParam String password) {

        User loggedUser;
        try {
            loggedUser = userService.login(email, password);
        } catch (UserNotExist userNotExist) {
            String exceptionMessage = userNotExist.getMessage();
            log.warn(exceptionMessage);
            return new Response<>("-1", exceptionMessage);
        } catch (PasswordIncorrect passwordIncorrect) {
            String exceptionMessage = passwordIncorrect.getMessage();
            log.warn(exceptionMessage);
            return new Response<>("-2", exceptionMessage);
        }

        UserDTO userDTO = new UserDTO(loggedUser);
        return new Response<>(userDTO);
    }

    @RequestMapping(value = "favourite", method = RequestMethod.POST)
    public Response<UserDTO> addFavourite(@RequestParam Long articleId,
                                          @RequestParam String token) {


        try {
            userService.addFavouriteArticle(articleId, token);
        } catch (UserNotExist userNotExist) {
            userNotExist.printStackTrace();
            return new Response<>("-1", userNotExist.getMessage());
        } catch (ArticleNotExist articleNotExist) {
            articleNotExist.printStackTrace();
            return new Response<>("-2", articleNotExist.getMessage());
        }

        return new Response<>("000", "OK");
    }

    @RequestMapping(value = "favourite", method = RequestMethod.GET)
    public Response<List<ArticleDTO>> getFavourites(@RequestParam String token) {


        List<Article> articleList;
        try {
            articleList = userService.findAllFavouriteArticles(token);
        } catch (UserNotExist userNotExist) {
            userNotExist.printStackTrace();
            return new Response<>("-1", userNotExist.getMessage());

        }
        List<ArticleDTO> articleDTOList = ArticleDTO.getList(articleList);

        return new Response<>(articleDTOList);
    }
}