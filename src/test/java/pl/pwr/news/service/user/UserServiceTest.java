package pl.pwr.news.service.user;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.model.user.User;
import pl.pwr.news.repository.user.UserRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by jf on 4/16/16.
 */
public class UserServiceTest {

    private static final String USERNAME = "testUsername";
    private static final String EMAIL = "testEmail";
    private static final String ACTIVATION_HASH = "testActivationHash";
    private static final String TOKEN = "testToken";
    private static final Long ID = 1L;
    private static final Long COUNT = 1L;
    private static User user = new User();
    private static final List<User> USER_LIST = Collections.singletonList(user);

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAll_existingUsers_listOfAllUsersReturned() {
        when(userRepository.findAll()).thenReturn(USER_LIST);
        List<User> userList = userService.findAll();
        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
        assertEquals(USER_LIST, userList);
    }

    @Test
    public void findById_existingUserId_userWithIdReturned() {
        when(userRepository.findOne(ID)).thenReturn(user);
        User foundUser = userService.findById(ID);
        verify(userRepository, times(1)).findOne(ID);
        verifyNoMoreInteractions(userRepository);
        assertEquals(user, foundUser);
    }

    @Test
    public void findByUsername_existingUserUsername_userWithUsernameReturned() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);
        User foundUser = userService.findByUsername(USERNAME);
        verify(userRepository, times(1)).findByUsername(USERNAME);
        verifyNoMoreInteractions(userRepository);
        assertEquals(user, foundUser);
    }

    @Test
    public void findByEmail_existingUserEmail_userWithEmailReturned() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(user);
        User foundUser = userService.findByEmail(EMAIL);
        verify(userRepository, times(1)).findByEmail(EMAIL);
        verifyNoMoreInteractions(userRepository);
        assertEquals(user, foundUser);
    }

    @Test
    public void findByActivationHash_existingUserActivationHash_userWithActivationHashReturned() {
        when(userRepository.findByActivationHash(ACTIVATION_HASH)).thenReturn(user);
        User foundUser = userService.findByActivationHash(ACTIVATION_HASH);
        verify(userRepository, times(1)).findByActivationHash(ACTIVATION_HASH);
        verifyNoMoreInteractions(userRepository);
        assertEquals(user, foundUser);
    }

    @Test
    public void findByToken_existingUserToken_userWithTokenReturned() {
        when(userRepository.findByToken(TOKEN)).thenReturn(user);
        User foundUser = userService.findByToken(TOKEN);
        verify(userRepository, times(1)).findByToken(TOKEN);
        verifyNoMoreInteractions(userRepository);
        assertEquals(user, foundUser);
    }

    @Test
    public void save_user_newAddedUserReturned() {
        when(userRepository.save(user)).thenReturn(user);
        userService.save(user);
        verify(userRepository, times(1)).save(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void generateActivateAccountUniqueHash_ExistingUser_generatedActivateAccountUniqueHashForUserReturned() {

    }

    @Test
    public void generateActivateAccountUniqueHash_NonExistingUser_generatedActivateAccountUniqueHashForUserReturned() {

    }
}
    //TODO: reszta testów po wejściu commita https://github.com/evelan/NewsWorldSpring/commit/139ee92724b2988076f545f7378ada93c47efe87
