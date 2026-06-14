package com.neoledger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.neoledger.dto.LoginRequest;
import com.neoledger.dto.LoginResponse;
import com.neoledger.dto.RegisterRequest;
import com.neoledger.entity.User;
import com.neoledger.service.UserService;
import com.neoledger.util.JwtUtil;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(
            @Validated @RequestBody RegisterRequest request) {

        User savedUser = userService.registerUser(request);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(
            @Validated @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                userService.loginUser(request));
    }

@GetMapping("/validate")
public ResponseEntity<String> validateToken(
        @RequestHeader("Authorization") String authHeader) {

    try {
        String token = authHeader.substring(7); // remove "Bearer "

        boolean isValid = JwtUtil.validateToken(token);

        if (isValid) {
            return ResponseEntity.ok("Token Valid");
        } else {
            return ResponseEntity.status(401).body("Invalid Token");
        }

    } catch (Exception e) {
        return ResponseEntity.status(401).body("Invalid Token");
    }
}
}