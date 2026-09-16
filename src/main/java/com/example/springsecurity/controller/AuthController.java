package com.example.springsecurity.controller;

import com.example.springsecurity.dto.MessageResponse;
import com.example.springsecurity.dto.RegisterRequest;
import com.example.springsecurity.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "AuthController")

public class AuthController {


    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "Register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.registerUser(request));
    }
}
