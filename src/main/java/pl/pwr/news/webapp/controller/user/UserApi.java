package pl.pwr.news.webapp.controller.user;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.user.User;
import pl.pwr.news.service.exception.*;
import pl.pwr.news.service.user.UserService;
import pl.pwr.news.webapp.controller.Response;
import pl.pwr.news.webapp.controller.article.dto.ArticleDTO;
import pl.pwr.news.webapp.controller.user.dto.UserDTO;

import java.util.List;

/**
 * Provides REST Api methods for @{@link User} account managment
 * @author Jakub Pomykała, Jacek Falfasiński, Jan Moroz, Rafał Pieniążek
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/api")
@Log4j
public class UserApi {

    @Autowired
    UserService userService;
    /**
     * Method for registratin user.
     * It consumes data provided in JSON format. It takes:
     * @param email
     * @param password
     * @param firstName
     * @param lastName
     *
     * This method returns registered User with status 200(success), or 405 HTTP message(conflict), if email is not unique.
     * Data returned are strictly connected with {@link UserDTO}
     * @return UserDTO
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
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

    /**
     *
     * @param email
     * @param password
     * @return UserDTO with credientals found in database.
     * If email does not exist in database an 401HTTP code(Unauthorized)  is returned
     * If email provided exist in databse, but password is incorect 401HTTP code is returned, with
     *"Bad Credentials" message
     * @see RegisterController for admin loging.
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
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

    /**
     * This method allows to save articles that user clicked to be his favourite.
     * It takes two params, in JSON format:
     * @param articleId
     * @param token - obtained from {@link #login(String,String)}.
     * @return UserDTO with User, or 401HTTP code if any User with provided token is found.
     *
     */
    @RequestMapping(value = "/user/favourite", method = RequestMethod.POST)
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

    /**
     * This method allows to obtain all articles which was clicked 'favourite' bu User.
     * It takes token as a param, in JSON format. Method will search User data with token provided
     *
     * @param token - obtained from {@link #login(String,String)}.
     * @return List of {@link ArticleDTO}
     *
     */
    @RequestMapping(value = "/user/favourite", method = RequestMethod.GET)
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

    /**
     * This method allows to increment tag's weigh, when user finds article interesting.
     * It takes two params, provided in JSON format.
     * @param tagId
     * @param token
     * Method will search User data with token provided and then add proper wage in connection beteen tables in databse.
     * @return value of actual tag weight for User, after inrementation.
     * It would return 401 HTTP message, when token not exist in database.
     */
    @RequestMapping(value = "/user/tag", method = RequestMethod.POST)
    public Response<Long> incrementTagValue(@RequestParam Long tagId, @RequestParam String token) {
        try {
            Long tagValue = userService.incrementTagValue(token, tagId);
            return new Response<>(tagValue);
        } catch (UserNotExist | TagNotExist ex) {
            return new Response<>("-1", ex.getMessage());
        }
    }
    /**
     * This method allows to obtain tag's weigh
     * It takes two params, provided in JSON format.
     * @param tagId
     * @param token
     * Method will search User data with token provided and then return wage for {@link pl.pwr.news.model.tag.Tag}.
     * @return value of actual tag weight for User found by Token
     * It would return 401 HTTP message, when token not exist in database.
     */
    @RequestMapping(value = "/user/tag", method = RequestMethod.GET)
    public Response<Long> getTagValue(@RequestParam Long tagId, @RequestParam String token) {
        try {
            Long tagValue = userService.getTagValue(token, tagId);
            return new Response<>(tagValue);
        } catch (UserTagNotExist userTagNotExist) {
            return new Response<>("-1", userTagNotExist.getMessage());
        }
    }
}
