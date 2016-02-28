package pl.pwr.news.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.pwr.news.model.user.User;

/**
 * Created by Rafal on 2016-02-28.
 */
interface UserService {


    User findById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByActivationHash(String activationHash);

    User findByToken(String token);

}
