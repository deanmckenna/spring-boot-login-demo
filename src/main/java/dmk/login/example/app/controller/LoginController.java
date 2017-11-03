package dmk.login.example.app.controller;

import dmk.login.example.app.security.UserDetails;
import dmk.login.example.app.utilities.RedirectViewFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

import static org.springframework.util.Assert.notNull;


@Controller
public class LoginController {

    static final String HOME_VIEW = "/home";
    static final String LOGIN_VIEW = "/login";

    private final RedirectViewFactory redirectViewFactory;

    @Autowired
    public LoginController(RedirectViewFactory redirectViewFactory) {
        notNull(redirectViewFactory, "RedirectViewFactory can not be null");
        this.redirectViewFactory = redirectViewFactory;
    }

    @RequestMapping("/")
    public RedirectView rootPathRequest() {
        RedirectView redirect;

        if (loggedIn()) {
            redirect = redirectViewFactory.createRedirectView(HOME_VIEW);
        } else {
            redirect = redirectViewFactory.createRedirectView(LOGIN_VIEW);
        }

        return redirect;
    }

    private boolean loggedIn() {
        boolean isLoggedIn = false;
        Optional<Authentication> auth = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());

        if (auth.isPresent()) {
            Object principal = auth.get().getPrincipal();

            if (principal != null && principal instanceof UserDetails && isValid((UserDetails) principal)) {
                isLoggedIn = true;
            }
        }


        return isLoggedIn;
    }

    private boolean isValid(UserDetails userDetails) {
        return (userDetails.isAccountNonExpired()) && (userDetails.isAccountNonLocked()) && (userDetails.isCredentialsNonExpired()) && (userDetails.isEnabled());
    }
}
