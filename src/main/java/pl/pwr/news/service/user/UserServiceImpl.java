package pl.pwr.news.service.user;

import org.apache.commons.codec.digest.DigestUtils;
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

/**
 * Created by Rafal on 2016-02-28.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
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
