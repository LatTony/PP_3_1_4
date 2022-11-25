package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImp implements UserService, UserDetailsService {

    private final UserDao userDao;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImp(UserDao userDao, RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
        addDefaultUser();
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.getUserByLogin(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("There is no user with this name");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities(user.getRoles()));
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public void updateUser(int id, User updatedUser) {
        User user = getUser(id);
        user.setName(updatedUser.getName());
        user.setSurname(updatedUser.getSurname());
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setRoles(updatedUser.getRoles());

        userDao.save(user);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public Collection<? extends GrantedAuthority> grantedAuthorities(Collection<Role> roles) {
        return roles.stream().map(el -> new SimpleGrantedAuthority(el.getName())).collect(Collectors.toList());
    }

    @Override
    public void addDefaultUser() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleService.findById(1));
        Set<Role> roleSet2 = new HashSet<>();
        roleSet2.add(roleService.findById(1));
        roleSet2.add(roleService.findById(2));
        User user1 = new User("user", "user", "user", "user", roleSet);
        User user2 = new User("admin", "admin", "admin", "admin", roleSet2);
        save(user1);
        save(user2);
    }
}

