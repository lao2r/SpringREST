package ru.crud.boot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.crud.boot.model.Role;
import ru.crud.boot.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    public RoleService roleService;

    @GetMapping
    public List<Role> findAll() {
        return roleService.findAllRoles();
    }
}
