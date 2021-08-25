package ru.crud.boot.service;

import ru.crud.boot.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAllRoles();

    Role findRoleById(Long id);

    Role findRoleByName(String role);

    void saveRole(Role role);


}
