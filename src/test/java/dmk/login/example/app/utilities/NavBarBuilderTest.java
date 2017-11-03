package dmk.login.example.app.utilities;


import dmk.login.example.app.domain.Option;
import dmk.login.example.app.domain.Role;
import dmk.login.example.app.utilities.NavBarBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;


public class NavBarBuilderTest {

    private NavBarBuilder classUnderTest;
    private ModelAndView modelAndView;
    private Map<Role, List<Option>> optionsMap;
    private List<Option> customerOptions;
    private List<Option> librarianOptions;

    @Mock
    private Authentication authentication;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        customerOptions = createCustomerOptions();
        librarianOptions = createLibrarianOptions();
        optionsMap = createOptionsMap();

        classUnderTest = new NavBarBuilder(optionsMap);
        modelAndView = new ModelAndView();
    }

    private List<Option> createCustomerOptions() {
        List<Option> options = new ArrayList<>();
        options.add(new Option("Borrow Book", "/book/borrow"));
        options.add(new Option("Return Book", "/book/return"));

        return options;
    }

    private List<Option> createLibrarianOptions() {
        List<Option> options = new ArrayList<>();
        options.add(new Option("add Book", "/book/add"));
        options.add(new Option("transfer Book", "/book/transfer"));

        return options;
    }

    private Map<Role, List<Option>> createOptionsMap() {
        Map<Role, List<Option>> optionMap = new HashMap<>();
        optionMap.put(Role.CUSTOMER, customerOptions);
        optionMap.put(Role.LIBRARIAN, librarianOptions);

        return optionMap;
    }

    @Test
    public void whenBuildNavBar_andUserIsCustomer_buildCustomerOptions() {
        createAuthorities(Role.CUSTOMER);
        classUnderTest.buildNavBar(modelAndView);

        assertEquals(customerOptions, getResult());
    }


    private void createAuthorities(Role... customer) {
        List<SimpleGrantedAuthority> authorities = Arrays.stream(customer).map(r -> new SimpleGrantedAuthority(r.value())).collect(Collectors.toList());
        doReturn(authorities).when(authentication).getAuthorities();

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private List<Option> getResult() {
        return (List<Option>) modelAndView.getModelMap().get(NavBarBuilder.OPTIONS);
    }

    @Test
    public void whenBuildNavBar_andUserIsCustomerAndLibrarian_combineOptions(){
        createAuthorities(Role.CUSTOMER, Role.LIBRARIAN);
        classUnderTest.buildNavBar(modelAndView);

        List<Option> expected = new ArrayList<>();
        expected.addAll(customerOptions);
        expected.addAll(librarianOptions);

        assertEquals(expected, getResult());
    }

    @Test
    public void whenBuildNavBar_andUserIsAdminAndLibrarian_andAdminNotConfigured_buildLibrarianOptions(){
        createAuthorities(Role.ADMIN, Role.LIBRARIAN);
        classUnderTest.buildNavBar(modelAndView);

        assertEquals(librarianOptions, getResult());
    }
}
