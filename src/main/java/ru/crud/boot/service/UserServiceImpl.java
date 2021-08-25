package ru.crud.boot.service;

import ru.crud.boot.model.User;
import ru.crud.boot.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleServiceImpl roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleServiceImpl roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findUserByEmail(String email) {
        return findAllUsers().stream().filter(u -> u.getEmail().equals(email)).findAny().orElse(null);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        String oldPassword = userRepository.findById(user.getId()).get().getPassword();
        String newPassword = user.getPassword();

        if (!oldPassword.equals(newPassword)) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(findUserById(id));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findUserByEmail(email);
        System.out.println(user);
        return user;
    }
}
