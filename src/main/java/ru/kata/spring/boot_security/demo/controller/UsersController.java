package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UsersController {

    private final UserServiceImp userServiceImp;

    @Autowired
    public UsersController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping()
    public String userShowInfo(Principal principal, Model model) {
        User authorizedUser = userServiceImp.findByUsername(principal.getName());
        User user = new User();
        model.addAttribute("userList", userServiceImp.getUsers());
        model.addAttribute("user", user);
        model.addAttribute("authorizedUser", authorizedUser);
        return "user";
    }
}
