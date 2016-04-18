package pl.pwr.news.service.user;

import pl.pwr.news.model.user.User;
import pl.pwr.news.webapp.controller.user.form.RegisterRequestBody;

import java.util.List;

/**
 * Created by Rafal on 2016-02-28.
 */
public interface UserService {

    void save(User user);

    List<User> findAll();

    User findById(Long id) throws UserNotExist;

    User findByUsername(String username) throws UserNotExist;

    User findByEmail(String email) throws UserNotExist;

    User findByActivationHash(String activationHash) throws UserNotExist;

    User findByToken(String token) throws UserNotExist;

    String generateActivateAccountUniqueHash(User user) throws UserNotExist;

    Long countAll();

    User createUserFromForm(RegisterRequestBody registerRequestBody);
}
