package pl.pwr.news.repository.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.news.ApplicationTests;
import pl.pwr.news.model.user.User;

import static org.junit.Assert.assertEquals;
import static pl.pwr.news.Constants.*;

/**
 * Created by jf on 4/14/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTests.class)
@Transactional
public class UserRepositoryTest {

    private static User user0 = new User();
    private static User user1 = new User();
    private static User user2 = new User();
    private static User user3 = new User();


    static {
        user0.setUsername(USERNAME);
        user1.setEmail(EMAIL);
        user2.setActivationHash(ACTIVATION_HASH);
        user3.setToken(TOKEN);
        user0.setRole(ROLE);
        user1.setRole(ROLE);
        user2.setRole(ROLE);
        user3.setRole(ROLE);
    }

    @Autowired
    UserRepository userRepository;

    @Test
    public void findByUsername_existingUserUsername_userWithUsernameReturned() {
        userRepository.save(user0);
        User foundUser = userRepository.findByUsername(USERNAME);
        assertEquals(user0, foundUser);
    }

    @Test
    public void findByEmail_existingUserEmail_userWithEmailReturned() {
        userRepository.save(user1);
        User foundUser = userRepository.findByEmail(EMAIL);
        assertEquals(user1, foundUser);

    }

    @Test
    public void findByActivationHash_existingUserActivationHash_userWithActivationHashReturned() {
        userRepository.save(user2);
        User foundUser = userRepository.findByActivationHash(ACTIVATION_HASH);
        assertEquals(user2, foundUser);

    }

    @Test
    public void findByToken_existingUserToken_userWithTokenReturned() {
        userRepository.save(user3);
        User foundUser = userRepository.findByToken(TOKEN);
        assertEquals(user3, foundUser);
    }
}