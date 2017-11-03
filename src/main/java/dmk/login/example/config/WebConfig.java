package dmk.login.example.config;

import dmk.login.example.app.domain.Option;
import dmk.login.example.app.domain.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Bean
    public Map<Role, List<Option>> navBarOptions(){
        Map<Role, List<Option>> navBarOptions = new HashMap<>();
        navBarOptions.put(Role.CUSTOMER, createCustomerOptions());
        navBarOptions.put(Role.LIBRARIAN, createLibrarianOptions());
        navBarOptions.put(Role.CO_LIBRARIAN, createCoLibrarianOptions());

        return navBarOptions;
    }

    private List<Option> createCustomerOptions() {
        List<Option> options = new ArrayList<>();
        options.add(new Option("Lend Book", "/lend"));
        options.add(new Option("Return Book", "/return"));
        options.add(new Option("Review Book", "/book/review"));
        options.add(new Option("Edit Profile", "/profile/edit"));

        return options;
    }

    private List<Option> createLibrarianOptions() {
        List<Option> options = new ArrayList<>();
        options.add(new Option("Add Book", "/book/add"));
        options.add(new Option("Edit Book", "book/edit"));
        options.add(new Option("Remove Book", "book/remove"));

        return options;
    }

    private List<Option> createCoLibrarianOptions() {
        List<Option> options = new ArrayList<>();
        options.add(new Option("Request Book Transfer", "/transfer"));

        return options;
    }
}
