package ru.crud.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.crud.boot.model.User;
import ru.crud.boot.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/users/{id}")
    public User findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        System.out.println("Update user in controller");
        userService.updateUser(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
