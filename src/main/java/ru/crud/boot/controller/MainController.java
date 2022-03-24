package ru.crud.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.crud.boot.model.User;
import ru.crud.boot.service.RoleService;

@Controller
public class MainController {

    private final RoleService roleService;

    @Autowired
    public MainController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String findAll(User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAllRoles());
        return "index";
    }

}