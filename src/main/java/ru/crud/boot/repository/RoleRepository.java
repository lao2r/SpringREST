package ru.crud.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.crud.boot.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}