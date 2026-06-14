 package com.neoledger.service;

import java.time.LocalDateTime;
import com.neoledger.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import com.neoledger.dto.LoginRequest;
import com.neoledger.dto.LoginResponse;
import com.neoledger.dto.RegisterRequest;

import com.neoledger.entity.Role;
import com.neoledger.entity.User;

import com.neoledger.repository.UserRepository;

@Service
public class UserServiceImpl
implements UserService {
	 

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder
    passwordEncoder;
    
    @Autowired
    private JwtService jwtService;

    @Override
    public User registerUser(
            RegisterRequest request) {

        User user =
                User.builder()

                .firstName(
                        request.getFirstName())

                .lastName(
                        request.getLastName())

                .email(
                        request.getEmail())

                .mobile(
                        request.getMobile())

                .password(

                        passwordEncoder
                        .encode(
                                request
                                .getPassword())

                )

                .role(
                        Role.CUSTOMER)

                .createdAt(
                        LocalDateTime.now())

                .build();

        return userRepository
                .save(user);
    }

    @Override
    public LoginResponse loginUser(
            LoginRequest request) {

        User user =
                userRepository
                .findByEmail(
                        request.getEmail())

                .orElseThrow(() ->

                        new RuntimeException(
                                "User not found"));

        if (!passwordEncoder.matches(

                request.getPassword(),

                user.getPassword()

        )) {

            throw new RuntimeException(
                    "Invalid Password");
        }

        String token =

                jwtService
                        .generateToken(
                                user.getEmail());

        return new LoginResponse(
                token,
                user.getRole().name(),
                user.getEmail()
        );
    }

}