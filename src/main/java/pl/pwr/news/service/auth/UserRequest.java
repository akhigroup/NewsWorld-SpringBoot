package pl.pwr.news.service.auth;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Rafal on 2016-03-26.
 * Simple POJO for authentication purposes
 * FIXME AM I In proper package??
 */
@JsonAutoDetect
@Getter
@Setter
public class UserRequest {
    @JsonProperty("username")
    String username;

    @JsonProperty("password")
    String password;
}
