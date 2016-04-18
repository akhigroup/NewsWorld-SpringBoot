package pl.pwr.news.service.user;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.pwr.news.model.user.User;
import pl.pwr.news.repository.user.UserRepository;

import java.util.Collections;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
    public void generateActivateAccountUniqueHash_userNull_nullReturned() {
        String hash = userService.generateActivateAccountUniqueHash(null);
        assertNull(hash);
    }

    @Test
    public void generateActivateAccountUniqueHash_userNotNull_generatedActivateAccountUniqueHashForUserReturned() {
        String hash = userService.generateActivateAccountUniqueHash(user);
        assertNotNull(hash);
    }

    @Test
    public void loadUserByUsername_nonExistingEmailexistingUsername_userDetailsReturned() {
        when(userRepository.findByEmail(USERNAME)).thenReturn(null);
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);
        UserDetails userDetails = userService.loadUserByUsername(USERNAME);
        verify(userRepository, times(1)).findByEmail(USERNAME);
        verify(userRepository, times(1)).findByUsername(USERNAME);
        verifyNoMoreInteractions(userRepository);
        assertNotNull(userDetails);
    }

    @Test
    public void loadUserByUsername_existingEmail_userDetailsReturned() {
        when(userRepository.findByEmail(USERNAME)).thenReturn(user);
        UserDetails userDetails = userService.loadUserByUsername(USERNAME);
        verify(userRepository, times(1)).findByEmail(USERNAME);
        verifyNoMoreInteractions(userRepository);
        assertNotNull(userDetails);
    }


    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_nonExistingUserUsername_exceptionThrown() {
        when(userRepository.findByEmail(USERNAME)).thenReturn(null);
        when(userRepository.findByUsername(USERNAME)).thenReturn(null);
        UserDetails userDetails = userService.loadUserByUsername(USERNAME);
        verify(userRepository, times(1)).findByEmail(USERNAME);
        verify(userRepository, times(1)).findByUsername(USERNAME);
        verifyNoMoreInteractions(userRepository);
        assertNull(userDetails);
    }
}
//TODO: createUserFromForm test + nullcase
