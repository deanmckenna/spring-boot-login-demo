package dmk.login.example.app.controller;

import dmk.login.example.app.controller.LoginController;
import dmk.login.example.app.domain.Role;
import dmk.login.example.app.domain.User;
import dmk.login.example.app.security.UserDetails;
import dmk.login.example.app.utilities.RedirectViewFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class LoginControllerTest {

    private LoginController classUnderTest;
    private RedirectViewFactory redirectView = new RedirectViewFactory(false);

    @Mock
    private Authentication authentication;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        classUnderTest = new LoginController(redirectView);
    }

    @Test
    public void whenRootPathRequest_andNoUserLoggedIn_redirectToLogin(){
        RedirectView result = classUnderTest.rootPathRequest();
        assertEquals(LoginController.LOGIN_VIEW, result.getUrl());
    }

    @Test
    public void whenRootPathRequest_andUserLoggedIn_redirectToLogin(){
        setUpUser();

        RedirectView result = classUnderTest.rootPathRequest();
        assertEquals(LoginController.HOME_VIEW, result.getUrl());
    }

    private void setUpUser() {
        Set<Role> roles = new HashSet<>();
        User user = new User("John", "doe", roles);
        UserDetails userDetails = new UserDetails(user);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @After
    public void tearDown(){
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
