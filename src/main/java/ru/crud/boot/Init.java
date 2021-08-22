package ru.crud.boot;


import ru.crud.boot.model.Role;
import ru.crud.boot.model.User;
import ru.crud.boot.service.AppService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component

public class Init {

    private final AppService appService;

    public Init(AppService appService) {
        this.appService = appService;
    }

    @PostConstruct
    public void loadData() {

        Role admin = new Role("ROLE_ADMIN");
        Role user = new Role("ROLE_USER");

        appService.saveRole(admin);
        appService.saveRole(user);

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

        appService.registerAdmin(user1);
        appService.registerDefaultUser(user2);
        appService.registerDefaultUser(user3);
        appService.registerDefaultUser(user4);
        appService.registerDefaultUser(user5);
    }
}
