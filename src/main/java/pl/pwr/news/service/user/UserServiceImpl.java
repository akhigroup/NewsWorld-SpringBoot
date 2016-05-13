package pl.pwr.news.service.user;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.model.user.Gender;
import pl.pwr.news.model.user.User;
import pl.pwr.news.model.user.UserRole;
import pl.pwr.news.model.usertag.UserTag;
import pl.pwr.news.repository.tag.TagRepository;
import pl.pwr.news.repository.user.UserRepository;
import pl.pwr.news.repository.usertag.UserTagRepository;
import pl.pwr.news.service.exception.*;
import pl.pwr.news.service.article.ArticleService;
import pl.pwr.news.webapp.controller.user.form.RegisterRequestBody;

import java.security.SecureRandom;
import java.util.*;

/**
 * Created by Rafal on 2016-02-28.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleService articleService;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    UserTagRepository userTagRepository;


    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void addFavouriteArticle(Long articleId, String userToken) throws UserNotExist, ArticleNotExist {
        Optional<User> userOptional = Optional.ofNullable(findByToken(userToken));

        if (StringUtils.isBlank(userToken))
            throw new IllegalArgumentException("UserToken cannot be empty or null");

        if (!userOptional.isPresent())
            throw new UserNotExist("User not exist for: " + userToken);

        User existingUser = userOptional.get();
        Optional<Article> articleOptional = Optional.ofNullable(articleService.findById(articleId));

        if (!articleOptional.isPresent())
            throw new ArticleNotExist(articleId);

        Article existingArticle = articleOptional.get();
        existingUser.addFavouriteArticle(existingArticle);

        userRepository.save(existingUser);
    }

    @Override
    public List<Article> findAllFavouriteArticles(String userToken) throws UserNotExist {
        Optional<User> userOptional = Optional.ofNullable(findByToken(userToken));

        if (StringUtils.isBlank(userToken))
            throw new IllegalArgumentException("UserToken cannot be empty or null");

        if (!userOptional.isPresent())
            throw new UserNotExist("User not exist for: " + userToken);

        User existingUser = userOptional.get();

        Set<Article> userFavouriteArticles = existingUser.getFavouriteArticles();

        return new ArrayList<>(userFavouriteArticles);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByActivationHash(String activationHash) {
        return userRepository.findByActivationHash(activationHash);
    }

    @Override
    public User findByToken(String token) {
        return userRepository.findByToken(token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if (user == null) {
            user = userRepository.findByUsername(username);
            if (user == null)
                throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
        }
        return user;
    }

    @Override
    public String generateActivateAccountUniqueHash(User user) {
        String hash = null;
        boolean isHashUnique;
        if (user != null) {
            do {
                SecureRandom random = new SecureRandom();
                hash = DigestUtils.md5Hex(user.getEmail() + random + user.getId());
                isHashUnique = userRepository.findByActivationHash(hash) != null;
            } while (isHashUnique);

        }
        return hash;
    }


    @Override
    public Long countAll() {
        return userRepository.count();
    }

    @Override
    public User createUserFromForm(RegisterRequestBody registerRequestBody) {
        User user = new User();
        user.setRole(UserRole.USER);
        user.setEmail(registerRequestBody.getMail());
        user.setUsername(registerRequestBody.getUsername());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(registerRequestBody.getPassword()));
        user.setRegistered(new Date());
        user.setEnabled(true);//TODO // activation mail

        return user;
    }

    @Override
    public User login(String email, String password) throws UserNotExist, PasswordIncorrect {

        if (!emailExist(email)) {
            throw new UserNotExist("User not exist with email: " + email);
        }

        User user = findByEmail(email);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String userPasswordHashed = user.getPassword();
        boolean passwordCorrect = encoder.matches(password, userPasswordHashed);
        if (!passwordCorrect) {
            throw new PasswordIncorrect("Password not match to email: " + email);
        }
        return user;

    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Long incrementTagValue(String userToken, Long tagId) throws UserNotExist, TagNotExist {
        Optional<UserTag> userTagOptional =
                Optional.ofNullable(userTagRepository.findOneByUser_TokenAndTag_Id(userToken, tagId));
        if (!userTagOptional.isPresent()) {
            addTag(userToken, tagId);
        }
        UserTag userTag = userTagRepository.findOneByUser_TokenAndTag_Id(userToken, tagId);
        userTag.incrementTagValue();
        userTagRepository.save(userTag);
        return userTag.getTagValue();
    }

    @Override
    public Long getTagValue(String userToken, Long tagId) throws UserTagNotExist {
        Optional<UserTag> userTagOptional =
                Optional.ofNullable(userTagRepository.findOneByUser_TokenAndTag_Id(userToken, tagId));
        if (!userTagOptional.isPresent()) {
            throw new UserTagNotExist("UserTag not exist for user token: " + userToken + " and tag id: " + tagId);
        }
        return userTagOptional.get().getTagValue();
    }

    private void addTag(String userToken, Long tagId) throws UserNotExist, TagNotExist {
        Optional<User> userOptional = Optional.ofNullable(findByToken(userToken));
        if (StringUtils.isBlank(userToken))
            throw new IllegalArgumentException("UserToken cannot be empty or null");
        if (!userOptional.isPresent())
            throw new UserNotExist("User not exist for: " + userToken);
        Optional<Tag> tagOptional = Optional.ofNullable(tagRepository.findOne(tagId));
        if (!tagOptional.isPresent())
            throw new TagNotExist("Tag not exist for: " + tagId);
        User existingUser = userOptional.get();
        Tag existingTag = tagOptional.get();
        existingUser.addTag(existingTag);
        userTagRepository.save(new UserTag(existingUser, existingTag));
        userRepository.save(existingUser);
    }

    @Override
    public String generateToken(User user) {
        String random = RandomStringUtils.randomAlphanumeric(128);
        return DigestUtils.md5Hex(user.getEmail() + "..::news::.." + random);
    }

    @Override
    public boolean emailExist(String email) {
        Optional<User> checkByEmail = Optional.ofNullable(findByEmail(email));
        return checkByEmail.isPresent();
    }

    @Override
    public boolean tokenExist(String token) {
        Optional<User> checkByToken = Optional.ofNullable(findByToken(token));
        return checkByToken.isPresent();
    }


    @Override
    public User facebookRegister(String email, String firstname, String lastname) throws EmailNotUnique {
        String password = RandomStringUtils.randomAlphanumeric(8);
        return register(email, password, firstname, lastname);
    }

    @Override
    public User register(String email, String password, String firstname, String lastname) throws EmailNotUnique {
        User user = new User();
        user.setRole(UserRole.ADMIN);
        user.setGender(Gender.UNKNOWN);

        if (emailExist(email)) {
            throw new EmailNotUnique("Email exist in database: " + email);
        }

        user.setEmail(email);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setRegistered(new Date());

        boolean tokenIsUnique;
        do {
            String token = generateToken(user);
            user.setToken(token);
            tokenIsUnique = !tokenExist(token);
        } while (!tokenIsUnique);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(password));

        userRepository.save(user);

        return user;
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }
}
