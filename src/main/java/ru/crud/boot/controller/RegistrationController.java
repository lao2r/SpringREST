package ru.crud.boot.controller;

import org.springframework.web.bind.annotation.RequestParam;
import ru.crud.boot.model.Role;
import ru.crud.boot.model.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.crud.boot.service.RoleService;
import ru.crud.boot.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final RoleService roleService;

    public RegistrationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        List<Role> listRoles = roleService.findAllRoles();

        model.addAttribute("userForm", new User());
        model.addAttribute("listRoles", listRoles);
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid @RequestParam("rolesId") List<Long> rolesId, User userForm, BindingResult bindingResult, Model model) {
        Set<Role> roles = new HashSet<>();
        List<Role> roleList = roleService.findAllRoles();

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        for (Long role : rolesId) {
            roles.add(roleService.findRoleById(role));
        }

        userForm.setRoles(roles);
        userService.saveUser(userForm);

        return "/login";
    }
}