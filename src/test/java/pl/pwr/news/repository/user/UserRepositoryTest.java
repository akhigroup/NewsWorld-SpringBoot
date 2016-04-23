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

    private static User user = new User();

    static {
        user.setId(ID);
        user.setUsername(USERNAME);
        user.setEmail(EMAIL);
        user.setActivationHash(ACTIVATION_HASH);
        user.setToken(TOKEN);
        user.setRole(ROLE);
    }

    @Before
    public void setup() {
        userRepository.save(user);
    }

    @Autowired
    UserRepository userRepository;

    @Test
    public void findByUsername_existingUserUsername_userWithUsernameReturned() {
        User foundUser = userRepository.findByUsername(USERNAME);
        assertEquals(user.getUsername(), foundUser.getUsername());
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getToken(), foundUser.getToken());
        assertEquals(user.getActivationHash(), foundUser.getActivationHash());
    }

    @Test
    public void findByEmail_existingUserEmail_userWithEmailReturned() {
        User foundUser = userRepository.findByEmail(EMAIL);
        assertEquals(user.getUsername(), foundUser.getUsername());
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getToken(), foundUser.getToken());
        assertEquals(user.getActivationHash(), foundUser.getActivationHash());
    }

    @Test
    public void findByActivationHash_existingUserActivationHash_userWithActivationHashReturned() {
        User foundUser = userRepository.findByActivationHash(ACTIVATION_HASH);
        assertEquals(user.getUsername(), foundUser.getUsername());
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getToken(), foundUser.getToken());
        assertEquals(user.getActivationHash(), foundUser.getActivationHash());
    }

    @Test
    public void findByToken_existingUserToken_userWithTokenReturned() {
        User foundUser = userRepository.findByToken(TOKEN);
        assertEquals(user.getUsername(), foundUser.getUsername());
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getToken(), foundUser.getToken());
        assertEquals(user.getActivationHash(), foundUser.getActivationHash());
    }
}
