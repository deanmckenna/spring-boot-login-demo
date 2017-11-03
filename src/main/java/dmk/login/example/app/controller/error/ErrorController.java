package dmk.login.example.app.controller.error;

import dmk.login.example.app.utilities.ErrorAttributesBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.springframework.util.Assert.notNull;


@Controller
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {

    private static Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    static final String ERROR_PATH = "/error";
    static final String ERROR = "error";
    static final String MESSAGE = "message";
    static final String ERROR_VIEW = "error";
    static final String STATUS = "status";


    private final ErrorAttributesBuilder errorAttributesBuilder;

    @Autowired
    public ErrorController(ErrorAttributesBuilder errorAttributesBuilder){
        notNull(errorAttributesBuilder, "ErrorAttributesBuilder can not be null");
        this.errorAttributesBuilder = errorAttributesBuilder;
    }

    @RequestMapping(ERROR_PATH)
    public ModelAndView errorHandler(HttpServletRequest request){
        Map<String, Object> errorDetails = errorAttributesBuilder.getErrorDetails(request);
        ModelAndView modelAndView = new ModelAndView(ERROR_VIEW);

        String message = createMessage(errorDetails);
        LOGGER.info(message);
        modelAndView.addObject(MESSAGE, message);

        return  modelAndView;
    }


    private String createMessage(Map<String, Object> errorDetails) {
        return errorDetails.get(STATUS) + " : " + errorDetails.get(ERROR);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
