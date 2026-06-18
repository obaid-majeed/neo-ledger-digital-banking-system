package com.neoledger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neoledger.entity.User;
import com.neoledger.service.UserService;
 @RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public User getProfile(
            @RequestParam String email) {

        return userService
                .getUserByEmail(email);
    }
    @GetMapping
    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }
}