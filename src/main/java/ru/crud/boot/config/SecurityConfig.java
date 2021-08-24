package ru.crud.boot.config;

import ru.crud.boot.config.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.crud.boot.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final LoginSuccessHandler loginSuccessHandler;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(@Qualifier("userService") UserService userService, LoginSuccessHandler loginSuccessHandler, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.loginSuccessHandler = loginSuccessHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/registration").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/login").anonymous()
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated();
        http.formLogin()
                .loginPage("/login")
                .permitAll()
//                .loginProcessingUrl("/login")
                .successHandler(loginSuccessHandler)
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll();
        http.logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout");
    }
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(12);
//    }

}
