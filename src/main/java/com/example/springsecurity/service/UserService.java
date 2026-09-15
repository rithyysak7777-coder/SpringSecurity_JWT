package com.example.springsecurity.service;

import com.example.springsecurity.dto.LoginResponse;
import com.example.springsecurity.dto.MessageResponse;
import com.example.springsecurity.dto.RegisterRequest;

public interface UserService {
    MessageResponse registerUser(RegisterRequest request);

    LoginResponse login(RegisterRequest request);
}
