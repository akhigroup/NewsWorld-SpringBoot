package pl.pwr.news.webapp.controller.user;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pwr.news.service.user.EmailNotUnique;
import pl.pwr.news.service.user.UserService;

import javax.inject.Inject;

/**
 * Created by Evelan on 23/04/16.
 */
@Controller
@RequestMapping("/")
@Log4j
public class FacebookApi {

    @Autowired
    UserService userService;
    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    @Inject
    public FacebookApi(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String register() {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }

        User facebookUserProfile = facebook.userOperations().getUserProfile();
        String email = facebookUserProfile.getEmail();
        String firstName = facebookUserProfile.getFirstName();
        String lastName = facebookUserProfile.getLastName();

        try {
            userService.facebookRegister(email, firstName, lastName);
        } catch (EmailNotUnique emailNotUnique) {
            log.warn(emailNotUnique.getMessage());
        }

        return "redirect:/login";
    }

}