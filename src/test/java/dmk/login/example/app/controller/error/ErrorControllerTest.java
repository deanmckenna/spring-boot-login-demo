package dmk.login.example.app.controller.error;

import dmk.login.example.app.controller.error.ErrorController;
import dmk.login.example.app.utilities.ErrorAttributesBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class ErrorControllerTest {

    private static final String STATUS_CODE = "502";
    private static final String ERROR_MESSAGE = "Bad Gateway";

    private ErrorController classUnderTest;
    private Map<String, Object> errorDetails;

    @Mock
    private ErrorAttributesBuilder errorAttributesBuilder;
    @Mock
    private HttpServletRequest request;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        errorDetails = createErrorDetails();
        when(errorAttributesBuilder.getErrorDetails(request)).thenReturn(errorDetails);

        classUnderTest = new ErrorController(errorAttributesBuilder);
    }

    private Map<String, Object> createErrorDetails() {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put(ErrorController.STATUS, STATUS_CODE);
        errorDetails.put(ErrorController.ERROR, ERROR_MESSAGE);

        return errorDetails;
    }

    @Test
    public void whenErrorOccurs_directToErrorView(){
        ModelAndView result = classUnderTest.errorHandler(request);
        assertEquals(ErrorController.ERROR_VIEW, result.getViewName());
    }

    @Test
    public void whenErrorOccurs_addErrorMessageToModel(){
        String expected = STATUS_CODE + " : " + ERROR_MESSAGE;
        ModelAndView result = classUnderTest.errorHandler(request);
        String actual = (String) result.getModelMap().get(ErrorController.MESSAGE);

        assertEquals(expected, actual);
    }

    @Test
    public void whenGetErrorPath_returnErrorPath(){
        assertEquals(ErrorController.ERROR_PATH, classUnderTest.getErrorPath());
    }

}
