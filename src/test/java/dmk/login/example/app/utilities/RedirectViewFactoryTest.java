package dmk.login.example.app.utilities;

import dmk.login.example.app.utilities.RedirectViewFactory;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.view.RedirectView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RedirectViewFactoryTest {

    private static final String PATH = "/home";

    private RedirectViewFactory classUnderTest;

    @Test
    public void whenExposedAttributesFalse_andRedirectWithPath_exposedAttributesSetToFalse() {
        classUnderTest = new RedirectViewFactory(false);
        RedirectView redirectView = classUnderTest.createRedirectView(PATH);

        boolean result = (boolean) ReflectionTestUtils.getField(redirectView, "exposeModelAttributes");
        assertFalse(result);
    }

    @Test
    public void whenExposedAttributesTrue_andRedirectWithPath_exposedAttributesSetToTrue(){
        classUnderTest = new RedirectViewFactory(true);
        RedirectView redirectView = classUnderTest.createRedirectView(PATH);

        boolean result = (boolean) ReflectionTestUtils.getField(redirectView, "exposeModelAttributes");
        assertTrue(result);
    }

    @Test
    public void whenCreateRedirectWithPath_createRedirectView_withPath(){
        classUnderTest = new RedirectViewFactory(false);
        RedirectView redirectView = classUnderTest.createRedirectView(PATH);

        assertEquals(PATH, redirectView.getUrl());
    }
}
