package ru.crud.boot.controller;

import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.crud.boot.model.User;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public String userInfo(@CurrentSecurityContext(expression = "authentication.principal") User principal, Model model) {
        model.addAttribute("user", principal);
        return "index";
    }
}
