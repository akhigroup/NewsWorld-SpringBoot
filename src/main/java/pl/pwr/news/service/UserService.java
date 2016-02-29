package pl.pwr.news.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.pwr.news.model.user.User;

/**
 * Created by Rafal on 2016-02-29.
 */
public interface UserService extends UserDetailsService {
    User findById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByActivationHash(String activationHash);

    User findByToken(String token);

    String generateActivateAccountUniqueHash(User user);

    void save(User user);

}
