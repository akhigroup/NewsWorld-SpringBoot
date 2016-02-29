package pl.pwr.news.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.user.CurrentUser;
import pl.pwr.news.model.user.User;
import pl.pwr.news.repository.user.UserRepository;

import java.util.List;

/**
 * Created by Rafal on 2016-02-28.
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

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

        return new CurrentUser(user);

    }


}
