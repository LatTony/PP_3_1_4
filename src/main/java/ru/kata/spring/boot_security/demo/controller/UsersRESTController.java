package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UsersRESTController {

    private final UserServiceImp userServiceImp;

    @Autowired
    public UsersRESTController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping("/rest")
    public ResponseEntity<User> userShowInfo(Principal principal) {
        return new ResponseEntity<>(userServiceImp.findByUsername(principal.getName()), HttpStatus.OK);
    }
}
