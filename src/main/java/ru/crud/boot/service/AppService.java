package ru.crud.boot.service;

import ru.crud.boot.model.Role;
import ru.crud.boot.model.User;
import ru.crud.boot.repository.RoleRepository;
import ru.crud.boot.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@Transactional
public class AppService implements UserDetailsService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AppService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public User findUserByEmail(String email) {
        return findAllUsers().stream().filter(u -> u.getEmail().equals(email)).findAny().orElse(null);
    }

    public Role findRoleById(Long id) {
        return roleRepository.findById(id).get();
    }

    public Role findRoleByName(String role) {
        return findAllRoles().stream().filter(r -> r.getName().equals(role)).findAny().orElse(null);
    }

    public boolean saveUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }

    public boolean saveRole(Role role) {
        roleRepository.save(role);
        return true;
    }

    public boolean registerAdmin(User user) {
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(findRoleByName("ROLE_ADMIN"));

        user.setRoles(adminRoles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }

    public boolean registerDefaultUser(User user) {
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(findRoleByName("ROLE_USER"));

        user.setRoles(userRoles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }

    public void deleteUser(Long id) throws NoSuchElementException {
        User user = findUserById(id);
        if (user == null) {
            throw new NoSuchElementException("User not found");
        }
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with email: %s not found", email));
        }
        System.out.println(user.toString());
        return user;
    }
}
