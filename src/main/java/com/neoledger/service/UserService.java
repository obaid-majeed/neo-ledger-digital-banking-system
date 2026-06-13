package com.neoledger.service;

import com.neoledger.dto.LoginRequest;
import com.neoledger.dto.LoginResponse;
import com.neoledger.dto.RegisterRequest;
import com.neoledger.entity.User;

public interface UserService {

    User registerUser(RegisterRequest request);

    LoginResponse loginUser(LoginRequest request);
}