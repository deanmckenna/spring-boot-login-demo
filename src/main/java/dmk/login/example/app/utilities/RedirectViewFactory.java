package dmk.login.example.app.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.RedirectView;


@Component
public class RedirectViewFactory {

    private final boolean exposeModelAttributes;

    public RedirectViewFactory(@Value("${security.redirect.expose.attributes:false}") boolean exposeModelAttributes) {
        this.exposeModelAttributes = exposeModelAttributes;
    }

    public RedirectView createRedirectView(String path){
        RedirectView redirectView = new RedirectView(path);
        redirectView.setExposeModelAttributes(exposeModelAttributes);

        return redirectView;
    }
}
