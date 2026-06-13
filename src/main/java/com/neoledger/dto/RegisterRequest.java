 package com.neoledger.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    @NotBlank
    private String mobile;

    @NotBlank
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }
}