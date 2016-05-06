package pl.pwr.news.service.user;

import pl.pwr.news.model.user.User;
import pl.pwr.news.webapp.controller.user.form.RegisterRequestBody;

import java.util.List;

/**
 * Created by Rafal on 2016-02-28.
 */
public interface UserService {

    void save(User user);

    String generateToken(User user);

    boolean emailExist(String email);

    boolean tokenExist(String token);

    User register(String email, String password, String firstname, String lastname) throws EmailNotUnique;

    User facebookRegister(String email, String firstname, String lastname) throws EmailNotUnique;

    List<User> findAll();

    User findById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByActivationHash(String activationHash);

    User findByToken(String token);

    String generateActivateAccountUniqueHash(User user);


    Long countAll();

    User createUserFromForm(RegisterRequestBody registerRequestBody);

    User login(String email, String password) throws UserNotExist, PasswordIncorrect;

}
