package dmk.login.example.app.service;

import dmk.login.example.app.dao.UserRepository;
import dmk.login.example.app.domain.Role;
import dmk.login.example.app.domain.User;
import dmk.login.example.app.security.UserDetails;
import dmk.login.example.app.service.UserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class UserDetailsServiceTest {

    private static final String USER_NAME = "John";

    private UserDetailsService classUnderTest;
    private User user;

    @Mock
    private UserRepository userRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        user = createUser();
        when(userRepository.findByName(USER_NAME)).thenReturn(user);

        classUnderTest = new UserDetailsService(userRepository);
    }

    private User createUser() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.CUSTOMER);

        return new User(USER_NAME, "doe", roles);
    }

    @Test
    public void whenLoadUserByName_andIsPresent_buildUserDetailsFromUser(){
        UserDetails expected = new UserDetails(user);
        assertEquals(expected, classUnderTest.loadUserByUsername(USER_NAME));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void whenLoadUserByName_andIsNotPresent_throwUsernameNotFoundException(){
        classUnderTest.loadUserByUsername("Jane");
    }
}
