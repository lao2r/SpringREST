package ru.crud.boot.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.crud.boot.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAllUsers();

    User findUserById(Long id);

    User findUserByEmail(String email);

    User saveUser(User user);

    User updateUser(User user);

    void deleteUser(Long id);

}
