package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;
import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImp userServiceImp;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserServiceImp userServiceImp, RoleService roleService) {
        this.userServiceImp = userServiceImp;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getUsers(Model model, Principal principal) {
        User authorizedUser = userServiceImp.findByUsername(principal.getName());
        User user = new User();
        model.addAttribute("userList", userServiceImp.getUsers());
        model.addAttribute("user", user);
        model.addAttribute("authorizedUser", authorizedUser);
        return "admin";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user, @RequestParam("roles") ArrayList<Integer> roles) {
        user.setRoles(roleService.findByIdRoles(roles));
        userServiceImp.save(user);

        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam("roles") ArrayList<Integer> roles,
                         @PathVariable("id") int id) {
        user.setRoles(roleService.findByIdRoles(roles));
        userServiceImp.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userServiceImp.delete(id);
        return "redirect:/admin";
    }
}