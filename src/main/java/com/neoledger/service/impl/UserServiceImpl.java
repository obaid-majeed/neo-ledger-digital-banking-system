package com.neoledger.service.impl;


import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.neoledger.dto.LoginRequest;
import com.neoledger.dto.LoginResponse;
import com.neoledger.dto.RegisterRequest;
import com.neoledger.entity.User;
import com.neoledger.repository.UserRepository;
import com.neoledger.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    private final String SECRET =
            "neoLedgerSecretKeyneoLedgerSecretKey";

    public User deposit(String email, BigDecimal amount) {

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        user.setBalance(
                user.getBalance().add(amount));

        return userRepository.save(user);
    }
    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegisterRequest request) {

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .password(encoder.encode(request.getPassword()))
                .role(com.neoledger.entity.Role.CUSTOMER)
                .createdAt(java.time.LocalDateTime.now())
                .balance(java.math.BigDecimal.ZERO)
                .build();

        return userRepository.save(user);
    }

    @Override
    public LoginResponse loginUser(LoginRequest request) {

        System.out.println("================================");
        System.out.println("LOGIN API HIT");
        System.out.println("EMAIL RECEIVED = [" +
                request.getEmail() + "]");
        System.out.println("PASSWORD RECEIVED = [" +
                request.getPassword() + "]");
        System.out.println("================================");

        System.out.println("USERS IN DATABASE:");

        userRepository.findAll().forEach(user ->
                System.out.println(
                        "USER => " + user.getEmail()));

        User user = userRepository.findByEmail(
                        request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        System.out.println(
                "FOUND USER => " + user.getEmail());

        if (!encoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Invalid password");
        }

        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60))
                .signWith(
                        Keys.hmacShaKeyFor(
                                SECRET.getBytes()))
                .compact();

        return new LoginResponse(
                token,
                user.getRole() != null
                        ? user.getRole().name()
                        : "CUSTOMER",
                user.getEmail()
        );
    }

    @Override
    public User getUserByEmail(String email) {

        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found"));
    }
}