package ru.crud.boot.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        System.out.println("555555555555555");
        if (roles.contains("ROLE_ADMIN")) {
            setDefaultTargetUrl("/admin");
        } else if (roles.contains("ROLE_USER")) {
            setDefaultTargetUrl("/user");
        }

            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

