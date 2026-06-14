package com.neoledger.service.impl;

import com.neoledger.dto.LoginRequest;
import com.neoledger.dto.LoginResponse;
import com.neoledger.dto.RegisterRequest;
import com.neoledger.entity.User;
import com.neoledger.repository.UserRepository;
import com.neoledger.service.UserService;
import com.neoledger.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final String SECRET =
            "neoLedgerSecretKeyneoLedgerSecretKey";
    public User deposit(String email, BigDecimal amount) {

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        user.setBalance(user.getBalance().add(amount));

        return userRepository.save(user);
    }

    @Override
    public User registerUser(RegisterRequest request) {

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public LoginResponse loginUser(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();

        return new LoginResponse(
                token,
                user.getRole().name(),
                user.getEmail()
        );
    }
}