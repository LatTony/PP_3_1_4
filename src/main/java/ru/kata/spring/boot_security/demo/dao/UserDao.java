package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User getUser(int id);

    User getUserByLogin(String username);

    void save(User user);

    void updateUser(User user);

    void delete(int id);
}
