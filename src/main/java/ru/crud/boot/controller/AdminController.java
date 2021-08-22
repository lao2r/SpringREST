package ru.crud.boot.controller;

import ru.crud.boot.model.Role;
import ru.crud.boot.model.User;
import ru.crud.boot.service.AppService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AppService appService;

    public AdminController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("")
    public String findAll(Model model){
        List<User> users = appService.findAllUsers();
        model.addAttribute("users", appService.findAllUsers());
        return "/user-list";
    }

    @GetMapping("/user-create")
    public String createUserForm(@ModelAttribute("user") User user) {
        return "/user-create";

    }

    @PostMapping("/user-create")
    public String createUser(User user) {
        appService.registerDefaultUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        appService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/user-update/{id}")
    public String updateUserForm(@PathVariable(value = "id", required = true) Long id, Model model) {
        User user = appService.findUserById(id);
        List<Role> listRoles = appService.findAllRoles();

        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "/user-update";
    }

    @PostMapping("/user-update")
    public String updateUser(@RequestParam("rolesId") List<Long> rolesId, User user) {
        setUserId(user);
        Set<Role> roles = new HashSet<>();

        for (Long role : rolesId) {
            roles.add(appService.findRoleById(role));
        }

        user.setRoles(roles);
        appService.saveUser(user);
        return "redirect:/admin";
    }

    private void setUserId(User user) {
        for (Role role : user.getRoles()) {
            role.setId(appService.findRoleByName(role.getName()).getId());
        }
    }
}