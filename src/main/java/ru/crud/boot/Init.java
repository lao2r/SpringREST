package ru.crud.boot;


import ru.crud.boot.model.Role;
import ru.crud.boot.model.User;
import org.springframework.stereotype.Component;
import ru.crud.boot.service.RoleServiceImpl;
import ru.crud.boot.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component

public class Init {

    private final RoleServiceImpl roleService;
    private final UserServiceImpl userService;

    public Init(RoleServiceImpl roleService, UserServiceImpl userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {

        Role admin = new Role("ROLE_ADMIN");
        Role user = new Role("ROLE_USER");

        roleService.saveRole(admin);
        roleService.saveRole(user);

        Set<Role> allAccess = new HashSet<>();
        Set<Role> userAccess = new HashSet<>();

        allAccess.add(admin);
        allAccess.add(user);

        userAccess.add(user);

        User user1 = new User("Admin", "Adminov", "admin", "admin", true);
        User user2 = new User("Anna", "Borovkova", "anna.borovkova@gmail.com", "1q2w3e", true);
        User user3 = new User("Boris", "Zakirov", "boris.zakirov@gmail.com", "05031987", true);
        User user4 = new User("Oleg", "Makarov", "oleg.makarov@gmail.com", "ghbdtn123", true);
        User user5 = new User("Igor", "Medvedev", "igor.medvedev@gmail.com", "drowssap", true);

        user1.setRoles(allAccess);
        user2.setRoles(userAccess);
        user3.setRoles(userAccess);
        user4.setRoles(userAccess);
        user5.setRoles(userAccess);
        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        userService.saveUser(user4);
        userService.saveUser(user5);
    }
}
