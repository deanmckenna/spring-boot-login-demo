package dmk.login.example.app.controller;

import dmk.login.example.app.utilities.NavBarBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


public class HomeControllerTest {

    private HomeController classUnderTest;

    @Mock
    private NavBarBuilder navBarBuilder;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        classUnderTest = new HomeController(navBarBuilder);
    }

    @Test
    public void whenHomeRequest_buildModelView_withHomeView(){
        ModelAndView result = classUnderTest.home();
        assertEquals(HomeController.VIEW, result.getViewName());
    }

    @Test
    public void whenHomeRequest_buildModelView_withNavBar(){
        classUnderTest.home();
        verify(navBarBuilder).buildNavBar(any(ModelAndView.class));
    }
}
