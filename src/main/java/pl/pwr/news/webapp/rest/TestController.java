package pl.pwr.news.webapp.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.pwr.news.model.user.User;
import pl.pwr.news.service.user.UserService;

import java.util.List;

/**
 * Created by Evelan-E6540 on 29.02.2016.
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class TestController {

    @Autowired
    UserService userService;

    @JsonProperty(required = false)
    @ApiModelProperty(notes = "User list")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userService.findAll();
    }
}
