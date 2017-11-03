package dmk.login.example.app.security;

import dmk.login.example.app.domain.Role;
import dmk.login.example.app.domain.User;
import dmk.login.example.app.security.UserDetails;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class UserDetailsTest {

    private static final String USER_NAME = "John";
    private static final String PASSWORD = "Doe";

    private UserDetails classUnderTest;
    private User user;

    @Before
    public void init(){
        user = createUser();
        classUnderTest = new UserDetails(user);
    }

    private User createUser() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.ADMIN);
        roleSet.add(Role.LIBRARIAN);

        return new User(USER_NAME, PASSWORD, roleSet);
    }

    @Test
    public void whenGetAuthorities_buildAuthoritiesFormUserRoles(){
        Collection<? extends GrantedAuthority> result = classUnderTest.getAuthorities();

        Set<Role> roles = result.stream()
                                .map(g -> Role.fromString(g.getAuthority()))
                                .filter(r -> r.isPresent())
                                .map(r -> r.get())
                                .collect(Collectors.toSet());
        assertEquals(roles, user.getRoles());
    }

    @Test
    public void whenGetUserName_returnUserNameFormUserObject(){
        assertEquals(USER_NAME, classUnderTest.getUsername());
    }

    @Test
    public void whenGetPassword_returnPasswordFromUserObject(){
        assertEquals(PASSWORD, classUnderTest.getPassword());
    }

    @Test
    public void whenHashCode_returnUserObjectHashCode(){
        assertEquals(user.hashCode(), classUnderTest.hashCode());
    }
}
