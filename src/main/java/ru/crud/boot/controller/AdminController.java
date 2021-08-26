package ru.crud.boot.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.crud.boot.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.crud.boot.service.RoleServiceImpl;
import ru.crud.boot.service.UserServiceImpl;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String findAll(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("listRoles", roleService.findAllRoles());
        return "index";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user, @RequestParam("listOfRoles") Long[] roleIds) {
        user.setRoles(Arrays.stream(roleIds).map(roleService::findRoleById).collect(Collectors.toSet()));
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @PatchMapping("/users/{id}")
    public String update(@ModelAttribute("user") User user, @RequestParam(value = "listOfRoles") Long[] rolesId, @AuthenticationPrincipal User authenticated) {
        user.setRoles(Arrays.stream(rolesId).map(roleService::findRoleById).collect(Collectors.toSet()));
        if (user.getUsername().equals(authenticated.getUsername())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

}