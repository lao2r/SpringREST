package ru.crud.boot.controller;

import ru.crud.boot.model.User;
import ru.crud.boot.service.AppService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final AppService appService;

    public RegistrationController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (!appService.registerDefaultUser(userForm)) { // <-- Регистрация пользователя
            model.addAttribute("usernameError", "User already exists");
            return "registration";
        }
        return "/login";
    }
}