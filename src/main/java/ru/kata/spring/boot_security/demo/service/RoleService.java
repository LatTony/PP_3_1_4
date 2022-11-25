package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    Role findRoleByName(String name);

    //
    List<Role> getAllRoles();

    void addRole(Role role);

    void addDefaultRole();

    Role findById(int id);

    Set<Role> findByIdRoles(List<Integer> roles);

}
