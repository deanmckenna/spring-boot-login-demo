package dmk.login.example.app.utilities;

import dmk.login.example.app.domain.Option;
import dmk.login.example.app.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.util.Assert.notNull;


@Component
public class NavBarBuilder {

    static final String OPTIONS = "options";

    private final Map<Role, List<Option>> optionMap;

    @Autowired
    public NavBarBuilder(@Qualifier("navBarOptions") Map<Role, List<Option>> optionMap) {
        notNull(optionMap, "OptionsMap can not be null");
        this.optionMap = optionMap;
    }

    public void buildNavBar(ModelAndView modelAndView) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            List<Option> options = authentication.getAuthorities().stream()
                                    .map(a -> Role.fromString(a.getAuthority()))
                                    .filter(r -> r.isPresent())
                                    .map(r -> r.get())
                                    .flatMap(this::lookUpOptions)
                                    .collect(Collectors.toList());

            modelAndView.addObject(OPTIONS, options);
        }
    }

    private Stream<Option> lookUpOptions(Role role) {
        Stream<Option> optionStream;

        if (optionMap.containsKey(role)) {
            optionStream = optionMap.get(role).stream();
        } else {
            optionStream = Stream.empty();
        }

        return optionStream;
    }
}
