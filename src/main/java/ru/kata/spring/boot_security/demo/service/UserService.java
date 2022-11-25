package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.GrantedAuthority;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    List<User> getUsers();

    void save(User user);

    User getUser(int id);

    void delete(int id);

    User findByUsername(String username);

    Collection<? extends GrantedAuthority> grantedAuthorities(Collection<Role> roles);

    void addDefaultUser();

    void updateUser(int id, User updatedUser);
}
