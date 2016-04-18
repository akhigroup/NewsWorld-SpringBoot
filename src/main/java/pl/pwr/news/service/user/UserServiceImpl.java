package pl.pwr.news.service.user;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.news.model.user.User;
import pl.pwr.news.model.user.UserRole;
import pl.pwr.news.repository.user.UserRepository;
import pl.pwr.news.webapp.controller.user.form.RegisterRequestBody;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Rafal on 2016-02-28.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    public User findById(Long id) throws UserNotExist {
        if (!userRepository.exists(id)) {
            throw new UserNotExist(id);
        }

        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) throws IllegalArgumentException, UserNotExist {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("User name cannot be empty or null");
        }

        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));

        if (!userOptional.isPresent()) {
            throw new UserNotExist("User not exist by username: " + username);
        }

        return userOptional.get();
    }

    @Override
    public User findByEmail(String email) throws UserNotExist {

        if (StringUtils.isBlank(email)) {
            throw new IllegalArgumentException("User email cannot be empty or null");
        }

        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));

        if (!userOptional.isPresent()) {
            throw new UserNotExist("User not exist by email: " + email);
        }

        return userOptional.get();
    }

    @Override
    public User findByActivationHash(String activationHash) throws UserNotExist {


        if (StringUtils.isBlank(activationHash)) {
            throw new IllegalArgumentException("User activation hash cannot be empty or null");
        }

        Optional<User> userOptional = Optional.ofNullable(userRepository.findByActivationHash(activationHash));

        if (!userOptional.isPresent()) {
            throw new UserNotExist("User not exist by activation hash: " + activationHash);
        }

        return userOptional.get();
    }

    @Override
    public User findByToken(String token) throws UserNotExist {

        if (StringUtils.isBlank(token)) {
            throw new IllegalArgumentException("User token cannot be empty or null");
        }

        Optional<User> userOptional = Optional.ofNullable(userRepository.findByToken(token));

        if (!userOptional.isPresent()) {
            throw new UserNotExist("User not exist by token: " + token);
        }

        return userOptional.get();
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
    public String generateActivateAccountUniqueHash(User user) throws UserNotExist {
        String hash;
        do {
            SecureRandom random = new SecureRandom();
            hash = DigestUtils.md5Hex(user.getEmail() + random + user.getId());

        } while (findByActivationHash(hash) != null);

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
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

}
