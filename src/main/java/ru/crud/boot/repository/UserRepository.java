package ru.crud.boot.repository;

import org.springframework.data.jpa.repository.Query;
import ru.crud.boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = :email")
    User findByEmail(String email);

}