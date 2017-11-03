package dmk.login.example.app.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.springframework.util.Assert.notNull;

@Component
public class ErrorAttributesBuilder {


    private final ErrorAttributes errorAttributes;

    @Autowired
    public ErrorAttributesBuilder(ErrorAttributes errorAttributes) {
        notNull(errorAttributes, errorAttributes.getClass() + " can not be null");
        this.errorAttributes = errorAttributes;
    }

    public Map<String, Object> getErrorDetails(HttpServletRequest request) {
        return errorAttributes.getErrorAttributes(new ServletRequestAttributes(request), true);
    }
}