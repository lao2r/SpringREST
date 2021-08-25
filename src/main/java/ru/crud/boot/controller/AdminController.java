package ru.crud.boot.controller;

import ru.crud.boot.model.Role;
import ru.crud.boot.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.crud.boot.service.RoleServiceImpl;
import ru.crud.boot.service.UserServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String findAll(Model model){
        model.addAttribute("users", userService.findAllUsers());
        return "/user-list";
    }

    @GetMapping("/user-create")
    public String createUserForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("listRoles", roleService.findAllRoles());
        return "/user-create";
    }

    @PostMapping("/user-create")
    public String createUser(@RequestParam("rolesId") List<Long> rolesId, User user) {
        Set<Role> roles = new HashSet<>();

        for (Long role : rolesId) {
            roles.add(roleService.findRoleById(role));
        }

        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/user-update/{id}")
    public String updateUserForm(@PathVariable(value = "id", required = true) Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("listRoles", roleService.findAllRoles());
        return "/user-update";
    }

    @PostMapping("/user-update")
    public String updateUser(@RequestParam("rolesId") List<Long> rolesId, @ModelAttribute User user) {
        Set<Role> roles = new HashSet<>();

        for (Long role : rolesId) {
            roles.add(roleService.findRoleById(role));
        }

        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }

}