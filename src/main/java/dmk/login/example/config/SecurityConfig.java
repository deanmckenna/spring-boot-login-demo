package dmk.login.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;


@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter implements EnvironmentAware {

    private static final String DEV = "dev";

    @Autowired
    private UserDetailsService userDetailsService;
    private Environment environment;


    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        configureHttpSecurity(httpSecurity);

        if(isDev()){
            httpSecurity.csrf().disable();
            httpSecurity.headers().frameOptions().disable();
        }
    }


    private void configureHttpSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().anyRequest().authenticated()
                .antMatchers("/").permitAll().and()
                .authorizeRequests().antMatchers("/h2/**").permitAll().and()
                .formLogin().loginPage("/login").permitAll().and()
                .logout().logoutSuccessUrl("/login?logout").permitAll();
    }

    private boolean isDev() {
        return Arrays.asList(environment.getActiveProfiles()).contains(DEV);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
