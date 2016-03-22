package pl.pwr.news.service.user;

import pl.pwr.news.model.user.User;

import java.util.List;

/**
 * Created by Rafal on 2016-02-28.
 */
public interface UserService {

    void save(User user);

    List<User> findAll();

    User findById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByActivationHash(String activationHash);

    User findByToken(String token);

    String generateActivateAccountUniqueHash(User user);

    Long countAll();
    
}
