package dmk.login.example.app.controller;

import dmk.login.example.app.utilities.NavBarBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.util.Assert.notNull;


@Controller
public class HomeController {

    static final String VIEW = "home";

    private final NavBarBuilder navBarBuilder;

    public HomeController(NavBarBuilder navBarBuilder){
        notNull(navBarBuilder, "NavBarBuilder can not be null");
        this.navBarBuilder = navBarBuilder;
    }

    @RequestMapping("/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView(VIEW);
        navBarBuilder.buildNavBar(modelAndView);

        return modelAndView;
    }
}
