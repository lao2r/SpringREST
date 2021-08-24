package ru.crud.boot.service;

import ru.crud.boot.model.Role;
import ru.crud.boot.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public Role findRoleById(Long id) {
        return roleRepository.findById(id).get();
    }

    public Role findRoleByName(String role) {
        return findAllRoles().stream().filter(r -> r.getName().equals(role)).findAny().orElse(null);
    }

    public void saveRole(Role role) {
        roleRepository.save(role);
    }

}
